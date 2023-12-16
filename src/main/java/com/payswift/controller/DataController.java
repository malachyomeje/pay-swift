package com.payswift.controller;

import com.payswift.dtos.response.BuyDataVariationCodeResponse;
import com.payswift.service.DataService;
import com.payswift.service.serviceImp.DataServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyingData")
public class DataController {

    private final DataService dataService;

    private final static Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    @GetMapping("/DataVariationCode")

    public BuyDataVariationCodeResponse DataVariationCode(@RequestParam String serviceID){
        LOGGER.info("ENTER DataCONTROLLER");
       return dataService.DataVariationCode(serviceID);


    }


}
