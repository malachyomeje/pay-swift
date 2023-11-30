package com.payswift.buying.service;

import com.payswift.buying.response.BuyAirtimeResponse;
import com.payswift.buying.response.QueryTransactionResponse;


public interface BuyAirtimeService {
    BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID);

    QueryTransactionResponse confirmBuyAirtime(String request_id);
}
