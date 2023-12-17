package com.payswift.dtos.request;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyElectricityDto {

    private String request_id;
    private String serviceID;
    private String billersCode;
    private String variation_code;
    private String amount;
    private String phone;


}
