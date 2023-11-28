package com.payswift.bank.bankDtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTransactionDto {
    private boolean status;
    private String message;

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
