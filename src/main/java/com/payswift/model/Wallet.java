package com.payswift.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

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
    private Double amount;
    private String accountNumber;
    private String pin;
    @UpdateTimestamp
    private Date dateUpdate;
    @CreationTimestamp
    private Date dateCreation;
   @OneToOne(mappedBy ="userWallet" )
    private Users walletUser;


}
