package com.payswift.service.paystackservice;

import com.payswift.dtos.externalapiDtos.response.VerifyTransactionDto;
import org.springframework.http.ResponseEntity;

public interface PayStackService {
    ResponseEntity<String> initializeTransaction(String transactionType, Double amount);

   // String verifyPayment(String reference);

    VerifyTransactionDto completeTransaction(String reference);

    // R String payment(String transactionType, Double amount);

    ;
}
