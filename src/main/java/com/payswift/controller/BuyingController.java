package com.payswift.controller;


import com.payswift.buying.response.BuyAirtimeResponse;
import com.payswift.buying.response.QueryTransactionResponse;
import com.payswift.buying.service.BuyAirtimeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/buying")
public class BuyingController {
 private final BuyAirtimeService buyAirtimeService;
    private final static Logger LOGGER = LoggerFactory.getLogger( UsersController.class);

    @PostMapping("/buyAirtime")
    public BuyAirtimeResponse buyAirtime(@RequestParam String phone, @RequestParam  Double amount, @RequestParam String serviceID){
        LOGGER.info("entered BuyingController");
        return buyAirtimeService.buyAirtime(phone,amount,serviceID);

    }
    @PostMapping("/confirmBuyAirtime")
    public QueryTransactionResponse confirmBuyAirtime(@RequestParam String request_id){
        return buyAirtimeService.confirmBuyAirtime(request_id);
    }
}



