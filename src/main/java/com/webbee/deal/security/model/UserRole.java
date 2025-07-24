package com.webbee.deal.security.model;

/**
 * Перечисление ролей пользователей в системе.
 */
public enum UserRole {

    /**
     * Базовая роль - может просматривать справочную информацию
     */
    USER,

    /**
     * Может просматривать сделки с типом CREDIT
     */
    CREDIT_USER,

    /**
     * Может просматривать сделки с типом OVERDRAFT
     */
    OVERDRAFT_USER,

    /**
     * Полный доступ к сервису deal
     */
    DEAL_SUPERUSER,

    /**
     * Полный доступ к сервису deal
     */
    SUPERUSER,

    /**
     * Полный доступ к сервису auth, без доступа к deal
     */
    ADMIN

}
