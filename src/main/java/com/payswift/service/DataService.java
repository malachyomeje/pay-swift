package com.payswift.service;

import com.payswift.dtos.request.BuyDataDto;
import com.payswift.dtos.response.BuyDataResponse;
import com.payswift.dtos.response.BuyDataVariationCodeResponse;

public interface DataService {


    BuyDataVariationCodeResponse DataVariationCode(String serviceID);

    BuyDataResponse BuyData(BuyDataDto buyDataDto);
}
