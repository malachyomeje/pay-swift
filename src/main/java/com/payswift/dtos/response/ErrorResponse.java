package com.payswift.dtos.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

    @Getter
    @Setter
    @Builder

public class ErrorResponse {
        private int httpStatusCode;
        private List<Object> message;
        private LocalDateTime responseDate;
    }


