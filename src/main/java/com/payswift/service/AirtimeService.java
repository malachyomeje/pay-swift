package com.payswift.service;

import com.payswift.dtos.response.BuyAirtimeResponse;
import com.payswift.dtos.response.QueryAirtimeTransactionResponse;


public interface AirtimeService {
    BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID);

    QueryAirtimeTransactionResponse confirmBuyAirtime(String request_id);
}
