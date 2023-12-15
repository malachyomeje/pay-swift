package com.payswift.dtos.response;

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

        private String ServiceName;
        private String serviceID;
        private String convinience_fee;
        private List<Variation> variations;



        @Getter
        @Setter
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Variation {

            private String variation_code;
            private String name;
            private String variation_amount;
            private String fixedPrice;


        }


    }


}

