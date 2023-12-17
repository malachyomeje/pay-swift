package com.payswift.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
    public class BuyElectricityResponse {
    private String code;
    private Content content;
    private String response_description;
    private String requestId;
    private String amount;
    private Transaction_date transaction_date;
    private String purchased_code;

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private Transactions transactions;
    }

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transactions {
        private int amount;
        private int convenienceFee;
        private String status;
        private String phone;
        private String email;
        private String type;
        private String commission;
        private String channel;
        private String platform;
        private String quantity;
        private String product_name;
    }

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction_date {
        private String date;
        private String timezone_type;
        private String timezone;
    }
}
