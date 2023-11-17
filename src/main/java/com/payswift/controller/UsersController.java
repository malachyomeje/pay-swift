package com.payswift.controller;

import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("appUser")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("signUpUser")
    public BaseResponse signUpUser(@RequestBody UsersDto usersDto){
        return usersService.signUpUser(usersDto);

    }
}


