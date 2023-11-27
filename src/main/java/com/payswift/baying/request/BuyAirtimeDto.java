package com.payswift.baying.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
