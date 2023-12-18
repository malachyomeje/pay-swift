package com.payswift.service;

import com.payswift.dtos.request.BuyElectricityDto;
import com.payswift.dtos.request.VerifyMeterNumberDto;
import com.payswift.dtos.response.BuyElectricityResponse;
import com.payswift.dtos.response.QueryElectricityTransactionResponse;
import com.payswift.dtos.response.VerifyMeterNumberResponse;

public interface ElectricityService {


    VerifyMeterNumberResponse VerifyMeterNumber(VerifyMeterNumberDto verifyMeterNumberDto);

    BuyElectricityResponse buyElectricity(BuyElectricityDto buyElectricityDto);

    QueryElectricityTransactionResponse confirmBuyElectricity(String request_id);
}
