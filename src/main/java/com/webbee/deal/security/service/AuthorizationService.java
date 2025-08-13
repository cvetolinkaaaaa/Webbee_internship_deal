package com.webbee.deal.security.service;

import com.webbee.deal.dto.DealSearchRequest;
import com.webbee.deal.security.model.UserRole;
import com.webbee.deal.security.model.TokenAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для проверки прав доступа пользователей.
 */
@Service
public class AuthorizationService {

    /**
     * Проверяет, может ли текущий пользователь просматривать сделки с определенным типом.
     */
    public boolean canViewDealsByType(String dealType) {
        Set<String> userRoles = getCurrentUserRoles();
        if (hasAnyRole(userRoles, UserRole.SUPERUSER, UserRole.DEAL_SUPERUSER)) {
            return true;
        }
        return switch (dealType.toUpperCase()) {
            case "CREDIT" -> hasRole(userRoles, UserRole.CREDIT_USER);
            case "OVERDRAFT" -> hasRole(userRoles, UserRole.OVERDRAFT_USER);
            default -> false;
        };
    }

    /**
     * Проверяет, может ли текущий пользователь выполнять операции записи с сделками.
     */
    public boolean canModifyDeals() {
        Set<String> userRoles = getCurrentUserRoles();
        return hasAnyRole(userRoles, UserRole.SUPERUSER, UserRole.DEAL_SUPERUSER);
    }

    /**
     * Проверяет, может ли текущий пользователь просматривать справочную информацию.
     */
    public boolean canViewReferenceData() {
        if (!isAuthenticated()) {
            return false;
        }

        Set<String> userRoles = getCurrentUserRoles();

        return hasAnyRole(userRoles,
                UserRole.USER,
                UserRole.CREDIT_USER,
                UserRole.OVERDRAFT_USER,
                UserRole.DEAL_SUPERUSER,
                UserRole.SUPERUSER
        );

    }

    /**
     * Проверяет, запрещен ли доступ к deal сервисам.
     */
    public boolean isDealContractorAccessDenied() {
        Set<String> userRoles = getCurrentUserRoles();
        return hasRole(userRoles, UserRole.ADMIN) && !hasRole(userRoles, UserRole.SUPERUSER);
    }

    /**
     * Применяет фильтрацию по правам доступа к запросу поиска сделок.
     */
    public DealSearchRequest applyDealAccessFilter(DealSearchRequest filter) {
        Set<String> userRoles = getCurrentUserRoles();
        if (hasAnyRole(userRoles, UserRole.SUPERUSER, UserRole.DEAL_SUPERUSER)) {
            return filter;
        }
        List<String> allowedTypes = userRoles.stream()
                .filter(role ->
                        role.equals("ROLE_CREDIT_USER") || role.equals("CREDIT_USER") ||
                                role.equals("ROLE_OVERDRAFT_USER") || role.equals("OVERDRAFT_USER"))
                .map(role -> {
                    if (role.contains("CREDIT")) {
                        return "CREDIT";
                    }
                    if (role.contains("OVERDRAFT")) {
                        return "OVERDRAFT";
                    }
                    return null;
                })
                .filter(type -> type != null)
                .collect(Collectors.toList());
        if (!allowedTypes.isEmpty()) {
            if (filter.getTypeIds() == null || filter.getTypeIds().isEmpty()) {
                filter.setTypeIds(allowedTypes);
            } else {
                List<String> filteredTypes = filter.getTypeIds().stream()
                        .filter(allowedTypes::contains)
                        .collect(Collectors.toList());
                filter.setTypeIds(filteredTypes);
            }
        } else {
            filter.setTypeIds(List.of());
        }

        return filter;
    }

    /**
     * Проверяет права доступа к сделке.
     */
    public boolean canAccessDeal(String dealTypeId) {
        if (dealTypeId == null) {
            return true;
        }

        Set<String> userRoles = getCurrentUserRoles();
        if (hasAnyRole(userRoles, UserRole.SUPERUSER, UserRole.DEAL_SUPERUSER)) {
            return true;
        }

        return canViewDealsByType(dealTypeId);
    }

    private Set<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof TokenAuthentication) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    private boolean hasRole(Set<String> userRoles, UserRole role) {
        return userRoles.contains("ROLE_" + role.name()) || userRoles.contains(role.name());
    }

    private boolean hasAnyRole(Set<String> userRoles, UserRole... roles) {
        for (UserRole role : roles) {
            if (hasRole(userRoles, role)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
    }

}
