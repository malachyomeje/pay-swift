package com.payswift.bank.bankDtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayStackResponse {

    private boolean status;
    private String message;
    private Data data;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class  Data{

        @JsonProperty("authorization_url")
        private String authorizationUrl;
        @JsonProperty("access_code")
        private String accessCode;
        private String reference;

    }

}


