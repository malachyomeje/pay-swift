package com.payswift.controller;


import com.payswift.baying.response.BuyAirtimeResponse;
import com.payswift.baying.service.BuyAirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/buying")
public class BuyingController {
 private final BuyAirtimeService buyAirtimeService;

    @PostMapping("/buyAirtime/{phone}/{amount}/{serviceID}")
    public BuyAirtimeResponse buyAirtime(@PathVariable String phone, @PathVariable Double amount, @PathVariable String serviceID){
        return buyAirtimeService.buyAirtime(phone,amount,serviceID);

    }
}



