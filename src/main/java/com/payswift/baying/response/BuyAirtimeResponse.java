package com.payswift.baying.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BuyAirtimeResponse {

    private Integer code;
    private String response_description;
    private String requestId;
    private String transactionId;
    private Double amount;
    private String purchased_code;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction_Date{
        private Date date;
        private String timezone_type;
        private String timezone;
    }

   // private String purchased_code;

}
