package com.payswift.paystack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VirtualAccountResponse {

   private boolean  status;
    private String    message;

    public VirtualAccountResponse(String message) {
        this.message = message;
    }
}
