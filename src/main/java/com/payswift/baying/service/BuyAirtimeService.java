package com.payswift.baying.service;

import com.payswift.baying.response.BuyAirtimeResponse;
import com.payswift.baying.response.QueryTransactionResponse;


public interface BuyAirtimeService {
    BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID);

    QueryTransactionResponse confirmBuyAirtime(String request_id);
}
