package com.payswift.bank.serviceImp;

import com.payswift.bank.bankDtos.request.PayStackRequestDto;
import com.payswift.bank.bankDtos.response.PayStackResponse;
import com.payswift.bank.service.PayStackService;
import com.payswift.enums.TransactionType;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Transaction;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.TransactionRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.utils.BankUtils;
import com.payswift.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static com.payswift.enums.TransactionStatus.COMPLETED;
import static com.payswift.enums.TransactionStatus.PENDING;
import static com.payswift.utils.BankUtils.*;
import static com.payswift.utils.UsersUtils.getAuthenticatedUserEmail;

@Service
@RequiredArgsConstructor
public class PayStackImp implements PayStackService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    // private String PaymentReference;
    // private Double fundingAmount;

    private final static Logger LOGGER = LoggerFactory.getLogger(PayStackImp.class);

    @Override
    public ResponseEntity<String> payment(String transactionType, Double amount) {

        LOGGER.info("ABOUT TO ENTER PAY_STACK");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        Users users1 = users.get();
        LOGGER.info("getting the user from security context{} ", users1);

        Optional<Wallet> findUserWallet = walletRepository.findById(users1.getUserWallet().getWalletId());
        if (findUserWallet.isEmpty()) {
            throw new WalletTransactionException("UserWallet not found");
        }
        Wallet userWallet = findUserWallet.get();

        PayStackRequestDto payStackRequestDto = new PayStackRequestDto();
        payStackRequestDto.setEmail(users1.getEmail());
        payStackRequestDto.setAmount(amount * 100);
        payStackRequestDto.setTransactionReference(BankUtils.generateTransactionReference());

        if (transactionType.equalsIgnoreCase("makepament")) {
            payStackRequestDto.setTransactionType(TransactionType.MAKEPAYMENT.getTransaction());
        } else {
            payStackRequestDto.setTransactionType(TransactionType.FUNDWALLET.getTransaction());
        }

        payStackRequestDto.setCallback_url(BankUtils.CALLBACK_URL+payStackRequestDto.getTransactionReference()
                +"/"+payStackRequestDto.getTransactionType());


        LOGGER.info("creating pay_stack_dto{} ", payStackRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);

        HttpEntity<PayStackRequestDto> entity = new HttpEntity<>(payStackRequestDto, headers);
        LOGGER.info("getting the entity{} ", entity);
        try {


            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<PayStackResponse> response = restTemplate.exchange
                    (PAY_STACK_DEPOSIT, HttpMethod.POST, entity, PayStackResponse.class);

            Transaction walletTransaction = Transaction.builder()

                    .name(users1.getFirstName() + " " + users1.getLastName())
                    .wallet(userWallet)
                    .transactionType(TransactionType.valueOf(payStackRequestDto.getTransactionType().toUpperCase()))
                    .transactionStatus(PENDING)
                    .amount(payStackRequestDto.getAmount())
                    .transactionReference(payStackRequestDto.getTransactionReference())
                    .build();

            transactionRepository.save(walletTransaction);
            //  LOGGER.info("getting the url{} ",response.getBody().getData().getAuthorizationUrl());
           return new ResponseEntity(response.getBody().getData().getAuthorizationUrl(), HttpStatus.ACCEPTED);

        } catch (HttpClientErrorException e) {
            LOGGER.info(e.getMessage());
            return new ResponseEntity<>("Failed to initiate transaction", e.getStatusCode());
        }

    }


    public String verifyPayment(String reference) {

       // String userEmail = UsersUtils.getAuthenticatedUserEmail();
        String userEmail = getAuthenticatedUserEmail();

        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        Users users = user.get();
        Optional<Wallet> wallet = walletRepository.findById(users.getUserWallet().getWalletId());
        if (wallet.isEmpty()){

            throw new WalletTransactionException("wallet not found");
        }
            Wallet wallet1 = wallet.get();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        Optional<Transaction> transaction = transactionRepository.findByTransactionReference(reference);
        if (transaction.isEmpty()) {
            throw new WalletTransactionException("Invalid Transaction Reference");
        }
        Transaction transaction1 = transaction.get();
        if (transaction1.getTransactionStatus().equals(COMPLETED))
            throw new WalletTransactionException("Transaction Already Completed");

            ResponseEntity<String> response = restTemplate.exchange(PAY_STACK_VERIFY_TRANSACTION + reference,
                    HttpMethod.GET, entity, String.class);

            if (response.getStatusCodeValue() == 200) {
                wallet1.setAccountBalance(transaction1.getAmount());
                walletRepository.save(wallet1);
            }
        transaction1.setTransactionStatus(COMPLETED);
        transactionRepository.save(transaction1);

        return "transaction failed";
    }
    }

