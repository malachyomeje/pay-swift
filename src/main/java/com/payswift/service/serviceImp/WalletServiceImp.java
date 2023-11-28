package com.payswift.service.serviceImp;

import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.WalletRepository;
import com.payswift.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImp implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void registerWallet(Users user) {
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

    @Override
    public PagingAndSortingResponse<List<Wallet>> walletSorting(String name) {
        List<Wallet> sortingWallet = walletRepository.findAll(Sort.by(Sort.Direction.ASC, name));
        return new PagingAndSortingResponse<>(sortingWallet.size(), sortingWallet);
    }

    @Override
    public PagingAndSortingResponse<Page<Wallet>> walletPagination(int offset, int pageSize) {
        Page<Wallet> walletPaging = walletRepository.findAll(PageRequest.of(offset, pageSize));
        return new PagingAndSortingResponse<>(walletPaging.getSize(), walletPaging);
    }

    @Override
    public PagingAndSortingResponse<Page<Wallet>> walletPaginationAndSorting(int offset, int pageSize, String name) {
        Page<Wallet> walletPagingAndSorting = walletRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(name)));
        return new PagingAndSortingResponse<>(walletPagingAndSorting.getSize(), walletPagingAndSorting);


    }

}
