package com.payswift.controller;


import com.payswift.dtos.request.BuyElectricityDto;
import com.payswift.dtos.request.VerifyMeterNumberDto;
import com.payswift.dtos.response.BuyElectricityResponse;
import com.payswift.dtos.response.QueryElectricityTransactionResponse;
import com.payswift.dtos.response.VerifyMeterNumberResponse;
import com.payswift.service.ElectricityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyingElectricity")
public class ElectricityController {

    private  final ElectricityService electricityService;

    @PostMapping("/VerifyMeterNumber")
    public VerifyMeterNumberResponse VerifyMeterNumber (@RequestBody VerifyMeterNumberDto verifyMeterNumberDto){
        return electricityService.VerifyMeterNumber(verifyMeterNumberDto);
    }

    @PostMapping("/buyElectricity")
    public BuyElectricityResponse buyElectricity (@RequestBody BuyElectricityDto buyElectricityDto) {
        return electricityService.buyElectricity(buyElectricityDto);

    }


    @PostMapping("/confirmBuyElectricity")
    public QueryElectricityTransactionResponse confirmBuyElectricity (@RequestParam String request_id){
        return electricityService.confirmBuyElectricity(request_id);
    }
}
