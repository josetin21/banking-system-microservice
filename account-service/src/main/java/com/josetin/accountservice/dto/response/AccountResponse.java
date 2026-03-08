package com.josetin.accountservice.dto.response;

import java.math.BigDecimal;

public record AccountResponse(

        String accountNumber,
        String ownerUsername,
        BigDecimal balance
) {
}
