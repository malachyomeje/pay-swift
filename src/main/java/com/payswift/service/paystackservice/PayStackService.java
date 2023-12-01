package com.payswift.service.paystackservice;

import com.payswift.dtos.externalapiDtos.response.VerifyTransactionResponse;
import org.springframework.http.ResponseEntity;

public interface PayStackService {
    ResponseEntity<String> initializeTransaction(String transactionType, Double amount);

   // String verifyPayment(String reference);

    VerifyTransactionResponse completeTransaction(String reference);

    // R String payment(String transactionType, Double amount);

    ;
}
