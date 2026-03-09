package com.josetin.transactionservice.controller;

import com.josetin.transactionservice.dto.response.TransactionResponse;
import com.josetin.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping("/{accountNumber}")
    public Page<TransactionResponse> getTransaction(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){

        return transactionRepository
                .findByAccountNumber(accountNumber, PageRequest.of(page,size))
                .map(tx-> new TransactionResponse(
                        tx.getAccountNumber(),
                        tx.getType(),
                        tx.getAmount(),
                        tx.getTimestamps()
                ));
    }
}
