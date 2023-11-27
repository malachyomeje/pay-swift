package com.payswift.model;


import com.payswift.enums.TransactionStatus;
import com.payswift.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private String name;
    private String transactionReference;
    private String recipient;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private Double amount;
    @UpdateTimestamp
    private Date updateDate;
    @CreationTimestamp
    private Date creationDate;
    @ManyToOne
    @JoinColumn(name = "wallet_wallet_id")
    private Wallet wallet;




}
