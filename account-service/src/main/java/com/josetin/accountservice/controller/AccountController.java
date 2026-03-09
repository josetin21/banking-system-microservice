package com.josetin.accountservice.controller;

import com.josetin.accountservice.dto.request.CreateAccountRequest;
import com.josetin.accountservice.dto.request.DepositRequest;
import com.josetin.accountservice.dto.request.TransferRequest;
import com.josetin.accountservice.dto.request.WithdrawRequest;
import com.josetin.accountservice.dto.response.AccountResponse;
import com.josetin.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponse creatAccount(@RequestBody CreateAccountRequest request){
        return accountService.createAccount(request);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse getAccount(@PathVariable String accountNumber){
        return accountService.getAccount(accountNumber);
    }

    @PostMapping("/{accountNumber}/deposit")
    public AccountResponse deposit(
            @PathVariable String accountNumber,
            @RequestBody DepositRequest request){
        return  accountService.deposit(accountNumber,request);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public AccountResponse withdraw(
            @PathVariable String accountNumber,
            @RequestBody WithdrawRequest request
            ){
        return accountService.withdraw(accountNumber,request);
    }

    @PostMapping("/transfer")
    public AccountResponse transfer(@RequestBody TransferRequest request){
        return accountService.transfer(request);
    }
}
