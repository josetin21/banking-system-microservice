package com.josetin.accountservice.service;

import com.josetin.accountservice.dto.request.CreateAccountRequest;
import com.josetin.accountservice.dto.response.AccountResponse;
import com.josetin.accountservice.entity.Account;
import com.josetin.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    public AccountResponse createAccount(CreateAccountRequest request){

        String accountNumber = "ACC-" + UUID.randomUUID().toString().substring(0,8);

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .ownerUsername(request.ownerUsername())
                .balance(request.initialBalance())
                .build();

        accountRepository.save(account);

        return new AccountResponse(
                account.getAccountNumber(),
                account.getOwnerUsername(),
                account.getBalance()
        );
    }
}
