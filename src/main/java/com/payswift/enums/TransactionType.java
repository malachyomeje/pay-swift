package com.payswift.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
// @Setter
@AllArgsConstructor
//@NoArgsConstructor
public enum TransactionType {

    CREDIT("credit"), DEBIT("debit"), FUNDWALLET("fundwallet"), WITHDRAWAL("withdrawal"), MAKEPAYMENT("makePayment");
    private final String transaction ;

}
