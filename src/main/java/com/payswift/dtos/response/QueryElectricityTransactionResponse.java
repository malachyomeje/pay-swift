package com.payswift.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryElectricityTransactionResponse {

    private String code;
    private Content content;
    private String response_description;
    private String requestId;
    private String amount;
    private Transaction_date transaction_date;
    private String purchased_code;
    private String amountField; // Note: I added a field to represent "Amount" from the JSON
    private String tax; // Note: I added a field to represent "Tax" from the JSON
    private String units;
    private String token;
    private String tariff;
    private String description;

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
        private String status;
        @JsonProperty("product_name")
        private String productName;
        @JsonProperty("unique_element")
        private String uniqueElement;
        @JsonProperty("unit_price")
        private String unitPrice;
        private String quantity;
        @JsonProperty("service_verification")
        private String serviceVerification;
        private String channel;
        private Double commission;
        @JsonProperty("total_amount")
        private String totalAmount;
        private Double discount;
        private String type;
        private String email;
        private String phone;
        private String name;
        @JsonProperty("convenience_fee")
        private String convenienceFee;
        private String amount;
        private String platform;
        private String method;
        @JsonProperty("transactionId")
        private String transactionId;

    }

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Transaction_date {
        private String date;
        @JsonProperty("timezone_type")
        private String timezoneType;
        private String timezone;

    }
}

