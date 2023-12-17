package com.payswift.controller;


import com.payswift.dtos.response.BuyAirtimeResponse;
import com.payswift.dtos.response.QueryAirtimeTransactionResponse;
import com.payswift.service.AirtimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyingAirtime")
public class AirtimeController {
 private final AirtimeService airtimeService;
    private final static Logger LOGGER = LoggerFactory.getLogger( UsersController.class);

    @PostMapping("/buyAirtime")
    public BuyAirtimeResponse buyAirtime(@RequestParam String phone, @RequestParam  Double amount, @RequestParam String serviceID){
        LOGGER.info("entered BuyingController");
        return airtimeService.buyAirtime(phone,amount,serviceID);

    }
    @PostMapping("/confirmBuyAirtime")
    public QueryAirtimeTransactionResponse confirmBuyAirtime(@RequestParam String request_id){
        return airtimeService.confirmBuyAirtime(request_id);
    }
}



