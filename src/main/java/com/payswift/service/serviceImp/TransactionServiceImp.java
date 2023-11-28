package com.payswift.service.serviceImp;

import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.model.Transaction;
import com.payswift.repository.TransactionRepository;
import com.payswift.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionService {

    private final TransactionRepository transactionRepository;
    @Override
    public PagingAndSortingResponse<List<Transaction>> transactionSorting(String name) {
        List<Transaction> sortingTransaction = transactionRepository.findAll(Sort.by(Sort.Direction.ASC, name));
        return new PagingAndSortingResponse<>(sortingTransaction.size(), sortingTransaction);
    }
    @Override
    public PagingAndSortingResponse<Page<Transaction>> transactionPagination(int offset, int pageSize) {
        Page<Transaction> transactionPaging =transactionRepository.findAll(PageRequest.of(offset, pageSize));
        return new PagingAndSortingResponse<>(transactionPaging.getSize(),transactionPaging);
    }

    @Override
    public PagingAndSortingResponse<Page<Transaction>> transactionPaginationAndSorting(int offset, int pageSize, String name) {
        Page<Transaction> transactionPagingAndSorting = transactionRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(name)));
        return new PagingAndSortingResponse<>(transactionPagingAndSorting.getSize(), transactionPagingAndSorting);

    }

}
