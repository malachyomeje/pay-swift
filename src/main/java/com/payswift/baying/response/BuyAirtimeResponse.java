package com.payswift.baying.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyAirtimeResponse {

    private Integer code;
    private String response_description;
    private String requestId;
    private String transactionId;
    private BigDecimal amount;
   // private String purchase_code;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionDate{
        private String date;
        private Integer timezone_type;
        private String timezone;
    }

    private String purchase_code;

}
