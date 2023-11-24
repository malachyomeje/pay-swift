package com.payswift.repository;

import com.payswift.model.Bank;
import com.payswift.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
