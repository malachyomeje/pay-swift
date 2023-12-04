package com.payswift.dtos.externalapiDtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTransactionResponse {
    private boolean status;
    private String message;
    private VerifyData verifyData;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyData {
        private  Long id;
        private String status;
        private String reference;
        private  Long amount;
        private String gateway_response;
        private Date paid_at;
    }
}
