package com.payswift.controller;

import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.request.AuthenticationRequest;
import com.payswift.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {



    private final AuthenticationService service;

    @PostMapping("/authenticate")
    public BaseResponse register(@RequestBody AuthenticationRequest request)

    {
        return service.authenticate(request);
    }
}


