package com.payswift.bank.bankDtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayStackRequestDto {

    private String email;
    private Double amount;

}
