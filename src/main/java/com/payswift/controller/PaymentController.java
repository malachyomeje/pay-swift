package com.payswift.controller;


import com.payswift.dtos.externalapiDtos.response.VerifyTransactionResponse;
import com.payswift.service.paystackservice.PayStackService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/paystack")
public class PaymentController {

    private final PayStackService paymentService;
    private final static Logger LOGGER = LoggerFactory.getLogger( PaymentController.class);

        @PostMapping("/account")

        public ResponseEntity<String> initializeTransaction(@RequestParam String transactionType, @RequestParam Double amount ) {
            LOGGER.info("entertainer BankController");
            return paymentService.initializeTransaction(transactionType, amount);
        }


        @GetMapping("/reference/{reference}")
    public VerifyTransactionResponse completeTransaction(@PathVariable String reference) {
            System.out.println("ENTER verifyPayment2 ");
            return paymentService.completeTransaction(reference);
    }
    }
