package com.payswift.service.flutterwaveservice;

import com.payswift.dtos.externalapiDtos.response.FlutterWaveResponse;

public interface FlutterWaveService {
    FlutterWaveResponse createAccount(String email);
}
