package com.josetin.accountservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotNull
        String ownerUsername,

        @NotNull
        @PositiveOrZero
        BigDecimal initialBalance
) {
}
