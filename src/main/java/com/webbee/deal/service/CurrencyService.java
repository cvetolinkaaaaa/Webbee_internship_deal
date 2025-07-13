package com.webbee.deal.service;

import com.webbee.deal.entity.Currency;
import com.webbee.deal.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Transactional(readOnly = true)
    public List<Currency> getAllActive() {
        return currencyRepository.findAll(); // или findByIsActiveTrue()
    }
}