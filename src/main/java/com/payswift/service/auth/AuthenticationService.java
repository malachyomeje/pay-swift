package com.payswift.service.auth;

import com.payswift.config.JwtService;
import com.payswift.dtos.request.AuthenticationRequest;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.model.Users;
import com.payswift.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {


    private final UsersRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public BaseResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
                 } catch (AuthenticationException e) {
            // Authentication failed, return "wrong password" response
            System.out.println(e);
                   return new BaseResponse<>("Wrong password  or email");
        }

        Optional<Users> users = repository.findByEmail(request.getEmail());
        if (users.isEmpty()) {
            return new BaseResponse<>("user not found", users);
        }
        Users users1 = users.get();
        var jwtToken = jwtService.generateToken(users1);
        return new BaseResponse<>("Successful", jwtToken);

    }
}


