package com.payswift.service;

import com.payswift.dtos.request.UpDatedUserDto;
import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsersService {
    BaseResponse signUpUser (UsersDto usersDto);

    BaseResponse confirmRegistration(String token);

    PagingAndSortingResponse<List<Users>> usersSorting(String name);

    PagingAndSortingResponse<Page<Users>> usersPagination(int offset, int pageSize);

    PagingAndSortingResponse<Page<Users>> usersPaginationAndSorting(int offset, int pageSize, String name);

    List<UsersDto>findAllUsers();

    BaseResponse findUserByEmail(String email);

    BaseResponse lockUserAccount(String email);

    BaseResponse deleteUserAccount(String email);

    BaseResponse updateUser(UpDatedUserDto upDatedUserDto);
}
