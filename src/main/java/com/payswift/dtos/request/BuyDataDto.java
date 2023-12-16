package com.payswift.dtos.request;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyDataDto {

    private  String request_id;
    private  String serviceID;
    private  String billersCode;
    private  String variation_code;
    private Double  amount;
    private String phone;



}
