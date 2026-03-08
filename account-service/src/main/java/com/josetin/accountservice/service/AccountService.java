package com.josetin.accountservice.service;

import com.josetin.accountservice.dto.request.CreateAccountRequest;
import com.josetin.accountservice.dto.request.DepositRequest;
import com.josetin.accountservice.dto.request.WithdrawRequest;
import com.josetin.accountservice.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(CreateAccountRequest request);
    AccountResponse getAccount(String accountNumber);
    AccountResponse deposit(String accountNumber, DepositRequest request);
    AccountResponse withdraw(String accountNumber, WithdrawRequest request);
}
