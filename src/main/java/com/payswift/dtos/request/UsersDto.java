package com.payswift.dtos.request;

import com.payswift.enums.Sex;
import com.payswift.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDto {

    @NotBlank(message = "firstName must not be empty")
    private String firstName;
    @NotBlank(message = "lastName must not be empty")
    private String lastName;
    @NotBlank(message = "lastName must not be empty")
    private String middleName;
    @NotBlank(message = "password must not be empty")
    private String password;
    @NotBlank(message = "walletPin must not be empty")
    private String walletPin;
    @Email(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
            ,message = "enter correct email")
    private String email;
    @NotBlank(message = "enter correct phoneNumber")
    private String accountNumber;
    private String phoneNumber;
//    @NotBlank(message = "age must not be empty")
//    private String age;
    @NotBlank(message = "country must not be empty")
    private String country;
  //  @NotBlank(message = "sex must not be empty")
    private Sex sex;
    private UserRole role;

  }
