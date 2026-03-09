package com.josetin.transactionservice.dto.response;

import com.josetin.transactionservice.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(

        String accountNumber,
        TransactionType type,
        BigDecimal amount,
        LocalDateTime timestamps
) {
}
