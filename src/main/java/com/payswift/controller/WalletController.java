package com.payswift.controller;

import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v5/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("sorting/{name}")
    public PagingAndSortingResponse<List<Wallet>> sorting (@PathVariable String name ) {
        return walletService.walletSorting(name);
    }
    @GetMapping("page/{offset}/{pageSize}")

    public PagingAndSortingResponse<Page<Wallet>> pagination(@PathVariable int offset, @PathVariable int pageSize){
        return walletService.walletPagination(offset,pageSize);
    }
    @GetMapping("page/{offset}/{pageSize}/{name}")
    public PagingAndSortingResponse<Page<Wallet>> paginationAndSorting(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String name) {
        return walletService.walletPaginationAndSorting(offset, pageSize, name);
    }
    @GetMapping("findUserWallet/{email}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public BaseResponse findUserWallet(@PathVariable String email){
        return  walletService.findUserWallet(email);
    }
}
