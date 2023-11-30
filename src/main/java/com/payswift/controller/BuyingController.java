package com.payswift.controller;


import com.payswift.baying.response.BuyAirtimeResponse;
import com.payswift.baying.service.BuyAirtimeService;
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
}



