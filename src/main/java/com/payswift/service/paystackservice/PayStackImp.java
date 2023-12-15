package com.payswift.service.paystackservice;

import com.payswift.dtos.externalapiDtos.request.PayStackRequestDto;
import com.payswift.dtos.externalapiDtos.response.PayStackResponse;
import com.payswift.dtos.externalapiDtos.response.VerifyTransactionResponse;
import com.payswift.enums.TransactionType;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Bank;
import com.payswift.model.Transaction;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.TransactionRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.enums.TransactionStatus.COMPLETED;
import static com.payswift.enums.TransactionStatus.PENDING;
import static com.payswift.utils.AppUtils.*;
import static com.payswift.utils.UsersUtils.getAuthenticatedUserEmail;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayStackImp implements PayStackService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;


    private final static Logger LOGGER = LoggerFactory.getLogger(PayStackImp.class);

    @Override
    public ResponseEntity<String> initializeTransaction(String transactionType, Double amount) {

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
        payStackRequestDto.setReference(AppUtils.generateTransactionReference());
        payStackRequestDto.setTransactionType(transactionType);
        payStackRequestDto.setCallback_url("www.google.com");

        if (transactionType.equalsIgnoreCase("makepayment")) {
            payStackRequestDto.setTransactionType(TransactionType.MAKEPAYMENT.getTransaction());
       } else {
           payStackRequestDto.setTransactionType(TransactionType.FUNDWALLET.getTransaction());
        }

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

            LOGGER.info("getting entity{} ", entity);
            Transaction walletTransaction = Transaction.builder()

                    .name(users1.getFirstName() + " " + users1.getLastName())
                    .wallet(userWallet)
                    .transactionType(TransactionType.valueOf(payStackRequestDto.getTransactionType().toUpperCase()))
                    .transactionStatus(PENDING)
                    .amount(amount+users1.getUserWallet().getAccountBalance())
                    .transactionReference(payStackRequestDto.getReference())
                    .build();
            transactionRepository.save(walletTransaction);
            LOGGER.info("saving transaction {} ", walletTransaction);
           return new ResponseEntity(response.getBody().getData().getAuthorizationUrl(), HttpStatus.ACCEPTED);


        } catch (HttpClientErrorException e) {
            LOGGER.info(e.getMessage());
            return new ResponseEntity<>("Failed to initiate transaction", e.getStatusCode());
        }

    }


    @Override
    public VerifyTransactionResponse completeTransaction(String reference) {

        String userEmail = getAuthenticatedUserEmail();

        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
            Users users = user.get();
        LOGGER.info("entering verifyPayment");

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);

            HttpEntity<String> entity = new HttpEntity<>( headers);
            RestTemplate restTemplate = new RestTemplate();

            Optional<Wallet> wallet = walletRepository.findById(users.getUserWallet().getWalletId());
        if(wallet.isEmpty()){
            throw  new WalletTransactionException("wallet not found");
        }
            Wallet wallet1= wallet.get();

        Optional<Transaction> transaction = transactionRepository.findByTransactionReference(reference);
        if(transaction.isEmpty()){
            throw  new WalletTransactionException("Invalid Transaction Reference");
            }

        String url = PAY_STACK_VERIFY_TRANSACTION+reference;

        LOGGER.info("Calling Paystack with URL: {}",url);

        ResponseEntity<VerifyTransactionResponse> response = restTemplate.exchange
                (url, HttpMethod.GET, entity, VerifyTransactionResponse.class);
        log.info("Response from Paystack Verification: {}", response);

        if(response.getBody()!=null){
            if(response.getBody().isStatus()){
                Transaction transaction1 = transaction.get();
                transaction1.setTransactionStatus(COMPLETED);

                wallet1.setAccountBalance(wallet1.getAccountBalance()+transaction1.getAmount());
                walletRepository.save(wallet1);
                Bank bank = users.getBank();
                bank.setAmount(bank.getAmount()+transaction1.getAmount());
            }

            LOGGER.error("Failed to verify payment");
        }

        return response.getBody();
    }

    }
