package com.josetin.accountservice.service;

import com.josetin.accountservice.client.TransactionClient;
import com.josetin.accountservice.dto.request.*;
import com.josetin.accountservice.dto.response.AccountResponse;
import com.josetin.accountservice.entity.Account;
import com.josetin.accountservice.entity.TransactionType;
import com.josetin.accountservice.exception.AccountNotFoundException;
import com.josetin.accountservice.exception.InsufficientBalanceException;
import com.josetin.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final TransactionClient transactionClient;

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

    @Override
    public AccountResponse getAccount(String accountNumber){
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return new AccountResponse(
                account.getAccountNumber(),
                account.getOwnerUsername(),
                account.getBalance()
        );
    }

    @Override
    @Transactional
    public AccountResponse deposit(String accountNumber, DepositRequest request) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(request.amount()));

        accountRepository.save(account);

        transactionClient.recordTransaction(
                new TransactionRequest(
                        account.getAccountNumber(),
                        TransactionType.DEPOSIT,
                        request.amount()
                )
        );

        return new AccountResponse(
                account.getAccountNumber(),
                account.getOwnerUsername(),
                account.getBalance()
        );
    }

    @Override
    @Transactional
    public AccountResponse withdraw(String accountNumber, WithdrawRequest request){
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new AccountNotFoundException("Account not found"));

        if(account.getBalance().compareTo(request.amount()) < 0){
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        account.setBalance(account.getBalance().subtract(request.amount()));

        accountRepository.save(account);

        transactionClient.recordTransaction(
                new TransactionRequest(
                        account.getAccountNumber(),
                        TransactionType.WITHDRAW,
                        request.amount()
                )
        );

        return new AccountResponse(
                account.getAccountNumber(),
                account.getOwnerUsername(),
                account.getBalance()
        );
    }

    @Override
    @Transactional
    public AccountResponse transfer(TransferRequest request){

        if (request.fromAccountNumber().equals(request.toAccountNumber())){
            throw new RuntimeException("Cannot transfer to same account");
        }

        Account fromAccount = accountRepository
                .findByAccountNumberForUpdate(request.fromAccountNumber())
                .orElseThrow(()->
                        new AccountNotFoundException("From account not found"));

        Account toAccount = accountRepository
                .findByAccountNumberForUpdate(request.toAccountNumber())
                .orElseThrow(()->
                        new AccountNotFoundException("To account not found"));

        if (fromAccount.getBalance().compareTo(request.amount()) < 0){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.amount()));

        toAccount.setBalance(toAccount.getBalance().add(request.amount()));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        transactionClient.recordTransaction(
                new TransactionRequest(
                        fromAccount.getAccountNumber(),
                        TransactionType.TRANSFER_OUT,
                        request.amount()
                )
        );

        transactionClient.recordTransaction(
                new TransactionRequest(
                        toAccount.getAccountNumber(),
                        TransactionType.TRANSFER_IN,
                        request.amount()
                )
        );

        return new AccountResponse(
                fromAccount.getAccountNumber(),
                fromAccount.getOwnerUsername(),
                fromAccount.getBalance()
        );
    }
}
