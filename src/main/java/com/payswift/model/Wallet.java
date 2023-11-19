package com.payswift.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long walletId;
    private BigDecimal amount;
    private String accountNumber;
    private String pin;
    @UpdateTimestamp
    private Date dateUpdate;
    @CreationTimestamp
    private Date dateCreation;
    @OneToOne
    private  Users userWallet;


}
