package com.payswift.repository;

import com.payswift.model.Transaction;
import com.payswift.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(String reference);
    Optional<Transaction>findByWallet(Wallet wallet);
}
