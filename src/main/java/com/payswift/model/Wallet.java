package com.payswift.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long walletId;
    private Double accountBalance;
    private String accountNumber;
    private String pin;
    @UpdateTimestamp
    private Date dateUpdate;
    @CreationTimestamp
    private Date dateCreation;
   @OneToOne(mappedBy ="userWallet" )
    private Users walletUser;

    @OneToMany (mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<Transaction> transactions;



}
