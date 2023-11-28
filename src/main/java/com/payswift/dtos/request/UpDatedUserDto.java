package com.payswift.dtos.request;

import com.payswift.enums.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpDatedUserDto {
    @NotBlank(message = "id must not be empty")
    private Long id;
    @NotBlank(message = "firstName must not be empty")
    private String firstName;
    @NotBlank(message = "lastName must not be empty")
    private String lastName;
    @NotBlank(message = "lastName must not be empty")
    private String middleName;
      @Email(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"
            ,message = "enter correct email")
    private String email;
    @NotBlank(message = "enter correct phoneNumber")
    private String phoneNumber;
    @NotBlank(message = "country must not be empty")
    private String country;
    private Sex sex;
}
