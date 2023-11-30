package com.payswift.buying.response;

import lombok.*;

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
    private Transaction_Date transaction_date;
    private Double amount;
    private String purchased_code;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction_Date{
        private String date;
        private String timezone_type;
        private String timezone;
    }


}
