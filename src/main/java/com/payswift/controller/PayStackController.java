//package com.payswift.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//@Service
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/paystack")
//public class PayStackController {
//
//    private final PayStackService paymentService;
//    private final static Logger LOGGER = LoggerFactory.getLogger( PayStackController.class);
//
//        @PostMapping("/account")
//
//        public VirtualAccountResponse payment(@RequestBody VirtualAccountDto users){
//            LOGGER.info("entertainer VirtualAccountResponse");
//            return paymentService.createAccount(users);
//        }
//
//    }
