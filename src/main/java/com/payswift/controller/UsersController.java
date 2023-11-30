package com.payswift.controller;

import com.payswift.dtos.request.UpDatedUserDto;
import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Users;
import com.payswift.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appUser")
public class UsersController {

    private final UsersService usersService;
    private final static Logger LOGGER = LoggerFactory.getLogger( UsersController.class);
    @PostMapping("/signAppUser")
    public BaseResponse signUpUser(@RequestBody UsersDto usersDto){
            LOGGER.info("entered UsersController");
        return usersService.signUpUser(usersDto);
    }

    @GetMapping("/confirmRegistration")
    public BaseResponse confirmRegistration(@RequestParam (name = "token") String token){
        LOGGER.info("[+]  Inside UsersController.confirmRegistration with payload {} ",token);
        return usersService.confirmRegistration(token);
    }
    @GetMapping("sorting/{name}")
    public PagingAndSortingResponse<List<Users>> sorting (@PathVariable String name ) {
        return usersService.usersSorting(name);
    }
    @GetMapping("page/{offset}/{pageSize}")
    public PagingAndSortingResponse<Page<Users>> pagination(@PathVariable int offset, @PathVariable int pageSize){
        return usersService.usersPagination(offset,pageSize);
    }
    @GetMapping("page/{offset}/{pageSize}/{name}")
    public PagingAndSortingResponse<Page<Users>> paginationAndSorting(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String name) {
        return usersService.usersPaginationAndSorting(offset, pageSize, name);
    }
    @GetMapping("/findAllUsers")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<UsersDto>findAllUsers(){
        return usersService.findAllUsers();
    }
    @GetMapping("findUserByEmail/{email}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public  BaseResponse findUserByEmail (@PathVariable String email){
        return usersService.findUserByEmail(email);
    }
    @GetMapping("lockUserAccount/{email}")
    public BaseResponse lockUserAccount(@PathVariable String email) {
        return usersService.lockUserAccount(email);
    }
    @DeleteMapping("deleteUserAccount/{email}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public  BaseResponse deleteUserAccount (@PathVariable String email){
        return  usersService.deleteUserAccount(email);
    }
    @PutMapping("/updateUse")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public  BaseResponse updateUser(@RequestBody UpDatedUserDto upDatedUserDto){
        return usersService.updateUser(upDatedUserDto);
    }
}


