package com.payswift.bank.service;

import com.payswift.bank.bankDtos.response.FlutterWaveResponse;

public interface FlutterWaveService {
    FlutterWaveResponse createAccount(String email);
}
