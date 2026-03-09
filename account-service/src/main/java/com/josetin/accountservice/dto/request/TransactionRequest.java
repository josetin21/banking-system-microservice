package com.josetin.accountservice.dto.request;

import com.josetin.accountservice.entity.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(
        String accountNumber,
        TransactionType type,
        BigDecimal amount
) {
}
