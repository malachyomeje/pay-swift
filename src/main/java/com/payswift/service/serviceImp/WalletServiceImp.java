package com.payswift.service.serviceImp;

import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.WalletRepository;
import com.payswift.service.WalletService;
import com.payswift.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public  void registerWallet(Users user){

        Wallet wallet = new Wallet();
       wallet.setAmount(BigDecimal.ZERO);
       wallet.setPin(user.getWalletPin());
      wallet.setPin(user.getWalletPin());
      user.setUserWallet(wallet);
       walletRepository.save(wallet);
    }

}
