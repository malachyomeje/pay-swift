package com.payswift.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BuyDataVariationCodeResponse {


    private String response_description;
    private Content content;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {

        @JsonProperty("ServiceName")
        private String ServiceName;
        private String serviceID;
        private String convinience_fee;
        @JsonProperty("varations")
        private List<Varations> varations;


        @Getter
        @Setter
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Varations {

            private String variation_code;
            private String name;
            private String variation_amount;
            private String fixedPrice;


        }


    }


}

