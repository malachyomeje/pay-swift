package com.payswift.bank.service;

import com.payswift.bank.bankDtos.response.PayStackResponse;
import org.springframework.http.ResponseEntity;

public interface PayStackService {
    ResponseEntity<String> payment(Double amount);
    // ResponseEntity<String> payment(String transactionType);

   // PayStackResponse payment(Double amount);
}
