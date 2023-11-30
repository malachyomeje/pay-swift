package com.payswift.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthenticationRequest {

    private  String email;
    private String password;

}
