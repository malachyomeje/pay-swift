package com.payswift.baying.service;

import com.payswift.baying.response.BuyAirtimeResponse;

public interface BuyAirtimeService {
    BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID);
}
