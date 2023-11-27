package com.payswift.service.serviceImp;

import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.WalletRepository;
import com.payswift.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public  void registerWallet(Users user){
        log.info("About to Wallet");

        Wallet wallet = new Wallet();
       wallet.setAccountBalance(0D);
       wallet.setPin(user.getWalletPin());
      wallet.setPin(user.getWalletPin());
      user.setUserWallet(wallet);
      wallet.setPin(user.getWalletPin());
      wallet.setAccountNumber(user.getBank().getAccountNumber());
      walletRepository.save(wallet);
       log.info("Created Wallet");
    }

}
