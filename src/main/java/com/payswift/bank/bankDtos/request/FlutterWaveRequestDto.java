package com.payswift.bank.bankDtos.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlutterWaveRequestDto {
    private String email;
    private Double amount;
}
