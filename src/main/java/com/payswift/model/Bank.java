package com.payswift.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("frequency")
    private String frequency;

    @JsonProperty("note")
    private String note;

    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("response_code")

    private String responseCode;

    @JsonProperty("response_message")
    private String responseMessage;

    @JsonProperty("flw_ref")
    private String flwRef;

    @JsonProperty("order_ref")
    private String orderRef;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("expiry_date")
    private String expiryDate;
}


