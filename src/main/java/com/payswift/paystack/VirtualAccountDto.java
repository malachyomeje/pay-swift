package com.payswift.paystack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirtualAccountDto {

  private String email;
    private String  firstName;
    private String  middleName;
    private String  lastName;
    private String   phone;
    private String preferredBank;
    private String country;
}
