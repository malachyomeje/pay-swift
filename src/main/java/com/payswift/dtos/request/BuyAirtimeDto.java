package com.payswift.dtos.request;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyAirtimeDto {

    private String request_id;
    private String serviceID;
    private Double  amount;
    private String phone;

}
