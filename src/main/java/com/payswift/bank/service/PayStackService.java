package com.payswift.bank.service;

import org.springframework.http.ResponseEntity;

public interface PayStackService {
    ResponseEntity<String> payment(String transactionType, Double amount);

    // R String payment(String transactionType, Double amount);

    ;
}
