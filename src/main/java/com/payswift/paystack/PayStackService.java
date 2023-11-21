package com.payswift.paystack;

import com.payswift.model.Users;

public interface PayStackService {
    //VirtualAccountResponse createAccount();

    VirtualAccountResponse createAccount(Users users);
}
