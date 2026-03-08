package com.josetin.accountservice.dto.request;

import java.math.BigDecimal;

public record WithdrawRequest(
        BigDecimal amount
) {
}
