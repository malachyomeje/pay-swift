package com.payswift.service;

import com.payswift.dtos.response.BuyAirtimeResponse;
import com.payswift.dtos.response.QueryTransactionResponse;


public interface AirtimeService {
    BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID);

    QueryTransactionResponse confirmBuyAirtime(String request_id);
}
