package com.payswift.service.serviceImp;

import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Transaction;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.TransactionRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UsersRepository usersRepository;

    @Override
    public PagingAndSortingResponse<List<Transaction>> transactionSorting(String name) {
        List<Transaction> sortingTransaction = transactionRepository.findAll(Sort.by(Sort.Direction.ASC, name));
        return new PagingAndSortingResponse<>(sortingTransaction.size(), sortingTransaction);
    }
    @Override
    public PagingAndSortingResponse<Page<Transaction>> transactionPagination(int offset, int pageSize) {
        Page<Transaction> transactionPaging = transactionRepository.findAll(PageRequest.of(offset, pageSize));
        return new PagingAndSortingResponse<>(transactionPaging.getSize(), transactionPaging);
    }

    @Override
    public PagingAndSortingResponse<Page<Transaction>> transactionPaginationAndSorting(int offset, int pageSize, String name) {
        Page<Transaction> transactionPagingAndSorting = transactionRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(name)));
        return new PagingAndSortingResponse<>(transactionPagingAndSorting.getSize(), transactionPagingAndSorting);

    }

@Override
    public BaseResponse findUserTransaction(String email) {
    String email1 = SecurityContextHolder.getContext().getAuthentication().getName();

    Optional<Users> users = usersRepository.findByEmail(email1);
        if (users.isEmpty()) {
            throw new UserNotFoundException("USER DOES NOT EXIST");
        }
        Users users3 =users.get();
        Optional<Wallet> wallet = walletRepository.findById(users3.getUserWallet().getWalletId());
        if (wallet.isEmpty()) {
            throw new WalletTransactionException("WALLET DOES NOT EXIST");
        }
        Wallet wallet1 = wallet.get();

        List<Transaction> transaction = wallet1.getTransactions();
    if (transaction .isEmpty()) {
        throw new WalletTransactionException("transaction DOES NOT EXIST");
    }
           return new BaseResponse<>("SSuccessful",transaction);
    }
}
