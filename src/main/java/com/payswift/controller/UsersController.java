package com.payswift.controller;

import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appUser")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signAppUser")
    public BaseResponse signUpUser(@RequestBody UsersDto usersDto){
        return usersService.signUpUser(usersDto);
    }

    @GetMapping("/confirmRegistration")
    public BaseResponse confirmRegistration(@RequestParam (name = "token") String token){
        return usersService.confirmRegistration(token);


    }
}


