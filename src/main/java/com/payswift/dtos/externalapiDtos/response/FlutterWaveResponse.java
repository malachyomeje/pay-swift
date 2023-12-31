package com.payswift.dtos.externalapiDtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlutterWaveResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;
    private Obj data;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Obj {
        @JsonProperty("response_code")
        private String responseCode;

        @JsonProperty("response_message")
        private String responseMessage;

        @JsonProperty("flw_ref")
        private String flwRef;

        @JsonProperty("order_ref")
        private String orderRef;

        @JsonProperty("account_number")
        private String accountNumber;

        private String frequency;

        @JsonProperty("bank_name")
        private String bankName;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("expiry_date")
        private String expiryDate;

        private String note;

        private Double amount;

    }
}


