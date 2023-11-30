package com.payswift.dtos.externalapiDtos.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlutterWaveRequestDto {
    private String email;
    private Double amount;
}
