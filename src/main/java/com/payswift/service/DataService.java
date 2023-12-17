package com.payswift.service;

import com.payswift.dtos.request.BuyDataDto;
import com.payswift.dtos.response.BuyDataResponse;
import com.payswift.dtos.response.BuyDataVariationCodeResponse;
import com.payswift.dtos.response.QueryDataTransactionResponse;

public interface DataService {


    BuyDataVariationCodeResponse DataVariationCode(String serviceID);

    BuyDataResponse BuyData(BuyDataDto buyDataDto);

    QueryDataTransactionResponse confirmDataTransaction(String request_id);
}
