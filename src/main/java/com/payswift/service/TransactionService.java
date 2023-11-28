package com.payswift.service;

import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    PagingAndSortingResponse<List<Transaction>> transactionSorting(String name);

    PagingAndSortingResponse<Page<Transaction>> transactionPagination(int offset, int pageSize);

    PagingAndSortingResponse<Page<Transaction>> transactionPaginationAndSorting(int offset, int pageSize, String name);


    BaseResponse findUserTransaction();
}
