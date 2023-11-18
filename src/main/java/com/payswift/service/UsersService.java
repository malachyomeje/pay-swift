package com.payswift.service;

import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;

public interface UsersService {
    BaseResponse signUpUser (UsersDto usersDto);

    BaseResponse confirmRegistration(String token);
}
