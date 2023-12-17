package com.payswift.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor

public enum TransactionType {

    CREDIT("credit"), DEBIT("debit"), BUY_AIRTIME("buy_airtime"), BUY_ELECTRICITY("buy_electricity"), BUY_DATA("buy_data"), FUND_WALLET("fund_wallet"),
    WITHDRAWAL("withdrawal"), MAKE_PAYMENT("make_payment");
    private final String transaction ;

}
