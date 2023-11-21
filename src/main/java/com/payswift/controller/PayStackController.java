package com.payswift.controller;

import com.payswift.model.Users;
import com.payswift.paystack.PayStackService;
import com.payswift.paystack.VirtualAccountResponse;
import com.payswift.service.serviceImp.UsersServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1/payStack")
public class PayStackController {

    private final PayStackService paymentService;
    private final static Logger LOGGER = LoggerFactory.getLogger( PayStackController.class);

        @PostMapping("/account")

        public VirtualAccountResponse payment(@RequestBody Users users){
            LOGGER.info("entertainer VirtualAccountResponse");
            return paymentService.createAccount(users);
        }

    }
