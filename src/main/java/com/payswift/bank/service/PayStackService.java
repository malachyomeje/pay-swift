package com.payswift.bank.service;

import com.payswift.bank.bankDtos.response.VerifyTransactionDto;
import org.springframework.http.ResponseEntity;

public interface PayStackService {
    ResponseEntity<String> payment(String transactionType, Double amount);

   // String verifyPayment(String reference);

    VerifyTransactionDto verifyPayment2(String reference);

    // R String payment(String transactionType, Double amount);

    ;
}
