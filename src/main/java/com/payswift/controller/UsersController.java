package com.payswift.controller;

import com.payswift.bank.serviceImp.PayStackImp;
import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appUser")
public class UsersController {

    private final UsersService usersService;
    private final static Logger LOGGER = LoggerFactory.getLogger( UsersController.class);
    @PostMapping("/signAppUser")
    public BaseResponse signUpUser(@RequestBody UsersDto usersDto){
            LOGGER.info("entertainer UsersController");
        return usersService.signUpUser(usersDto);
    }

    @GetMapping("/confirmRegistration")
    public BaseResponse confirmRegistration(@RequestParam (name = "token") String token){
        LOGGER.info("[+]  Inside UsersController.confirmRegistration with payload {} ",token);
        return usersService.confirmRegistration(token);


    }
}


