package com.payswift.service;

import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WalletService {
    void registerWallet (Users user);

    PagingAndSortingResponse<List<Wallet>> walletSorting(String name);

    PagingAndSortingResponse<Page<Wallet>> walletPagination(int offset, int pageSize);

    PagingAndSortingResponse<Page<Wallet>> walletPaginationAndSorting(int offset, int pageSize, String name);
}
