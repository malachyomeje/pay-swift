package com.payswift.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthenticationRequestDto {

    private  String email;
    private String password;

}
