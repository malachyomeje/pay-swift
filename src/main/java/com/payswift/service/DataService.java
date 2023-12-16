package com.payswift.service;

import com.payswift.dtos.response.BuyDataVariationCodeResponse;

public interface DataService {


    BuyDataVariationCodeResponse DataVariationCode(String serviceID);
}
