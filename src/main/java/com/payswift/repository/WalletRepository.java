package com.payswift.repository;

import com.payswift.model.Users;
import com.payswift.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByAccountNumber(String accountNumber );

}
