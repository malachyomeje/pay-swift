package com.payswift.dtos.response;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryDataTransactionResponse {
    private String code;
    private Content content;
    private String response_description;
    private String requestId;
    private String amount;
    private TransactionDate transaction_date;
    private String purchased_code;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private Transactions transactions;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Transactions {
        private String status;
        private String product_name;
        private String unique_element;
        private String unit_price;
        private String quantity;
        private String service_verification;
        private String channel;
        private String commission;
        private String total_amount;
        private String discount;
        private String type;
        private String email;
        private String phone;
        private String name;
        private String convinience_fee;
        private String amount;
        private String platform;
        private String method;
        private String transactionId;

    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionDate {
        private String date;
        private String timezone_type;
        private String timezone;

    }
}

