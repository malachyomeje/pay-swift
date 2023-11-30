package com.payswift.dtos.response;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public  class QueryTransactionResponse {

    private  String code;
    private Content content;
    private  String  response_description;
    private  String  requestId;
    private  String  amount;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
 public static class Content{
        private  Transactions transactions;

        @Getter
        @Setter
        @ToString
        @AllArgsConstructor
       @NoArgsConstructor

 public static class Transactions{
    private String  status;
    private String product_name;
    private String unique_element;
    private String  unit_price;
    private String  quantity;
    private String type;
    private String  email;
    private String phone;
    private String  amount;
    private String transactionId;
        }


    }


}
