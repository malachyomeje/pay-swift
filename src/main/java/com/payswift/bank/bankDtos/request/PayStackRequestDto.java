package com.payswift.bank.bankDtos.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PayStackRequestDto {


    private Double amount;
    private String email;
    private String reference;
    private String transactionType;
    private String callback_url;
    private String authorization_url;

}
