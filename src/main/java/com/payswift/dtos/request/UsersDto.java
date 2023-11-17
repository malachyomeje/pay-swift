package com.payswift.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDto {

    @NotBlank(message = "firstName must not be empty")
    private String firstName;
    @NotBlank(message = "lastName must not be empty")
    private String lastName;
    @NotBlank(message = "lastName must not be empty")
    private String otherName;
    @NotBlank(message = "password must not be empty")
    private String password;
    @Email(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
            ,message = "enter correct email")
    private String email;
    @NotBlank(message = "enter correct phoneNumber")
    private String phoneNumber;
    @NotBlank(message = "age must not be empty")
    private String age;
    @NotBlank(message = "sex must not be empty")
    private String sex;
  }
