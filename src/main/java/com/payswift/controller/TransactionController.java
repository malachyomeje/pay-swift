package com.payswift.controller;

import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Transaction;
import com.payswift.model.Wallet;
import com.payswift.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private  final TransactionService transactionService;
    @GetMapping("/sorting/{name}")
    public PagingAndSortingResponse<List<Transaction>> sorting (@PathVariable String name ) {
        return transactionService.transactionSorting(name);
    }
    @GetMapping("/page/{offset}/{pageSize}")
    public PagingAndSortingResponse<Page<Transaction>> pagination(@PathVariable int offset, @PathVariable int pageSize){
        return transactionService.transactionPagination(offset,pageSize);
    }
    @GetMapping("/page/{offset}/{pageSize}/{name}")
    public PagingAndSortingResponse<Page<Transaction>> paginationAndSorting(@PathVariable int offset, @PathVariable int pageSize,@PathVariable String name) {
        return transactionService.transactionPaginationAndSorting(offset, pageSize, name);
    }
    @GetMapping("/findUserTransaction/{email}")
    public BaseResponse findUserTransaction(@PathVariable String email) {
        return transactionService.findUserTransaction(email);
    }
}
