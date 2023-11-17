package com.payswift.service;

import com.payswift.dtos.request.EmailDto;

public interface EmailService {
    void sendEmail(EmailDto emailDto);
}
