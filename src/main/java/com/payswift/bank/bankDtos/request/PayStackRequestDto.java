package com.payswift.bank.bankDtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayStackRequestDto {

    private String email;
    private Double amount;
    private String  transactionType;

}
