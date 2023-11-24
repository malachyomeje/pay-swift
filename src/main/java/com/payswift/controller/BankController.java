package com.payswift.controller;


import com.payswift.bank.service.PayStackService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
@RequiredArgsConstructor
@RequestMapping("/api/v2/paystack")
public class BankController {

    private final PayStackService paymentService;
    private final static Logger LOGGER = LoggerFactory.getLogger( BankController.class);

        @PostMapping("/account")

        public ResponseEntity<String> payment( @RequestParam Double amount) {
            LOGGER.info("entertainer VirtualAccountResponse");
            return paymentService.payment(amount);
        }

    }