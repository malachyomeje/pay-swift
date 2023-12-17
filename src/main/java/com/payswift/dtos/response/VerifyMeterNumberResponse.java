package com.payswift.dtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VerifyMeterNumberResponse {

    private String code;
    private Content content;


    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        @JsonProperty("Customer_Name")
        private String customerName;
        @JsonProperty("Account_Number")
        private String accountNumber;
        @JsonProperty("Meter_Number")
        private String meterNumber;
        @JsonProperty("Business_Unit")
        private String businessUnit;
        @JsonProperty("Address")
        private String address;
        @JsonProperty("Customer_Arrears")
        private String customerArrears;
    }
}
