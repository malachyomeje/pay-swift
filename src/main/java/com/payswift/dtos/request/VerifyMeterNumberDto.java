package com.payswift.dtos.request;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VerifyMeterNumberDto {

    private String billersCode;
    private String serviceID;
    private String type;

}
