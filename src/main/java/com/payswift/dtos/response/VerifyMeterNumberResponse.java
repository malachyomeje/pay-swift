package com.payswift.dtos.response;


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
        private String customerName;
        private String accountNumber;
        private String meterNumber;
        private String businessUnit;
        private String address;
        private String customerArrears;
    }
}

