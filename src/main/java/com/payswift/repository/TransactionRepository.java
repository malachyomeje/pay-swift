package com.payswift.repository;

import com.payswift.model.Transaction;
import com.payswift.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
