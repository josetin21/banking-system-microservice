package com.josetin.transactionservice.dto.request;

import com.josetin.transactionservice.entity.TransactionType;

import java.math.BigDecimal;

public record TransactionRequest(
        String accountNumber,
        TransactionType type,
        BigDecimal amount
) {
}
