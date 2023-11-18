package com.payswift.controller;

import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/appUser")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("signUpUser")
    public BaseResponse signUpUser(@RequestBody UsersDto usersDto){
        return usersService.signUpUser(usersDto);

    }


    @PostMapping("confirmRegistration/{token}")
    public BaseResponse confirmRegistration(@PathVariable String token){
        return usersService.confirmRegistration(token);


    }
}


