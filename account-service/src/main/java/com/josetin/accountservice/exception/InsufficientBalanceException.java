package com.josetin.accountservice.exception;

public class InsufficientBalanceException extends  RuntimeException{

    public InsufficientBalanceException(String message){
        super(message);
    }
}
