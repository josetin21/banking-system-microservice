package com.josetin.accountservice.service;

import com.josetin.accountservice.dto.request.CreateAccountRequest;
import com.josetin.accountservice.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);
}
