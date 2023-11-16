package com.payswift.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private String message;
    private T data;

    public BaseResponse(String message) {
        this.message = message;
    }
}

