package com.payswift.controller;


import com.payswift.baying.response.BuyAirtimeResponse;
import com.payswift.baying.service.BuyAirtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/buying")
public class BuyingController {
 private final BuyAirtimeService buyAirtimeService;

    @PostMapping("/buyAirtime")
    public BuyAirtimeResponse buyAirtime(@RequestParam String phone, @RequestParam  Double amount, @RequestParam String serviceID){
        return buyAirtimeService.buyAirtime(phone,amount,serviceID);

    }
}



