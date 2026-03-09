package com.josetin.accountservice.client;

import com.josetin.accountservice.dto.request.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class TransactionClient {

    private final RestTemplate restTemplate;

    public void recordTransaction(TransactionRequest request){

        restTemplate.postForObject(
                "http://localhost:8083/api/transaction",
                request,
                void.class
        );
    }
}
