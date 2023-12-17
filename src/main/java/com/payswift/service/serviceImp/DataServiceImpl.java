package com.payswift.service.serviceImp;


import com.payswift.dtos.request.BuyDataDto;
import com.payswift.dtos.request.EmailDto;
import com.payswift.dtos.request.QueryDataTransactionResponseDto;
import com.payswift.dtos.response.BuyDataResponse;
import com.payswift.dtos.response.BuyDataVariationCodeResponse;
import com.payswift.dtos.response.QueryDataTransactionResponse;
import com.payswift.enums.TransactionType;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Transaction;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.TransactionRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.service.DataService;
import com.payswift.service.EmailService;
import com.payswift.utils.UsersUtils;
import com.payswift.utils.VTPassUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.enums.TransactionStatus.COMPLETED;
import static com.payswift.enums.TransactionStatus.PENDING;
import static com.payswift.utils.VTPassUtils.*;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;

    private final static Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);


    @Override
    public BuyDataVariationCodeResponse DataVariationCode(String serviceID){
        LOGGER.info("ENTERED  DataServiceImpl");

        String userEmail = UsersUtils.getAuthenticatedUserEmail();
        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found");

        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("public-key", PUBLIC_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        LOGGER.info("Calling DataVariationCode with HEADER: {}",headers);

        HttpEntity<String> entity = new HttpEntity<>( headers);
        RestTemplate restTemplate = new RestTemplate();

        String url = GET_VARIATION_CODES+serviceID;

        LOGGER.info("Calling DataVariationCode with URL: {}",url);

        ResponseEntity<BuyDataVariationCodeResponse> response = restTemplate.exchange
                (url, HttpMethod.GET, entity, BuyDataVariationCodeResponse.class);
        LOGGER.info("Calling DataVariationCode with ENTITY: {}",entity);
        LOGGER.info("Calling DataVariationCode with RESPONSE: {}",response);

        return response.getBody();
    }

    @Override
    public BuyDataResponse BuyData(BuyDataDto buyDataDto){

        String userEmail = UsersUtils.getAuthenticatedUserEmail();
        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        Users users1 = user.get();

        Optional<Wallet> findUserWallet = walletRepository.findById(users1.getUserWallet().getWalletId());
        if (findUserWallet.isEmpty()) {
            throw new WalletTransactionException("UserWallet not found");
        }
        Wallet userWallet = findUserWallet.get();

        if (userWallet.getAccountBalance()<buyDataDto.getAmount()){
            throw new WalletTransactionException("INSUFFICIENT BALANCE,FUND-WALLET");
        }
        BuyDataDto buyDataDto1 = new BuyDataDto();
        buyDataDto1.setRequest_id(VTPassUtils.generateRequestId());
        buyDataDto1.setServiceID(buyDataDto.getServiceID());
        buyDataDto1.setBillersCode(buyDataDto.getBillersCode());
        buyDataDto1.setVariation_code(buyDataDto.getVariation_code());
        buyDataDto1.setAmount(buyDataDto.getAmount());
        buyDataDto1.setPhone(buyDataDto.getPhone());
        LOGGER.info("Entering buyDataDto with entity: {}", buyDataDto1);

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuyDataDto> entity = new HttpEntity<>(buyDataDto1, headers);
        LOGGER.info("Calling vtpass with entity: {}", entity);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BuyDataResponse> response = restTemplate.exchange
                (BUY_DATA, HttpMethod.POST, entity, BuyDataResponse.class);

        Transaction walletTransaction = Transaction.builder()
                .name(users1.getFirstName() + " " + users1.getLastName())
                .wallet(userWallet)
                .transactionType(TransactionType.BUY_DATA)
                .transactionStatus(PENDING)
                .amount(buyDataDto1.getAmount())
                .transactionReference(buyDataDto1.getRequest_id())
                .build();
        transactionRepository.save(walletTransaction);
        return response.getBody();
    }

    @Override
    public QueryDataTransactionResponse confirmDataTransaction(String request_id){

        LOGGER.info("entered confirmBuyData");

        String userEmail = UsersUtils.getAuthenticatedUserEmail();
        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        Users user1 = user.get();
        Optional<Wallet> findUsersWallet = walletRepository.findById(user1.getUserWallet().getWalletId());

        if (findUsersWallet.isEmpty()) {
            throw new WalletTransactionException("UserWallet not found");
        }
        Wallet userWallet = findUsersWallet.get();

        Optional<Transaction> transaction3 = transactionRepository.findByTransactionReference(request_id);
        if (transaction3.isEmpty()) {
            throw new WalletTransactionException("Invalid Transaction Reference");
        }

        QueryDataTransactionResponseDto queryDataTransactionResponseDto = new QueryDataTransactionResponseDto();
       queryDataTransactionResponseDto.setRequest_id(request_id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<QueryDataTransactionResponseDto> entity = new HttpEntity<>(queryDataTransactionResponseDto, headers);

        RestTemplate restTemplate = new RestTemplate();
        LOGGER.info("Calling vtpass with entity: {}", entity);

        ResponseEntity<QueryDataTransactionResponse> response = restTemplate.exchange
                (CONFIRM_DATA, HttpMethod.POST, entity, QueryDataTransactionResponse.class);

        if (response.getBody() != null) {
            if (response.getBody().getContent().getTransactions().getStatus().equals("delivered")) {
                Transaction transaction1 = transaction3.get();
                transaction1.setTransactionStatus(COMPLETED);
                userWallet.setAccountBalance(userWallet.getAccountBalance()-transaction1.getAmount());

                transactionRepository.save(transaction1);
                walletRepository.save(userWallet);

                String subject = "Pay-Swift BUY_DATA";
                try {
                    EmailDto emailSenderDto = new EmailDto();
                    emailSenderDto.setTo(user1.getEmail());
                    emailSenderDto.setSubject(subject);
                    emailSenderDto.setContent(transaction1.getAmount().toString());
                    emailService.sendEmail(emailSenderDto);

                }catch (Exception ex){
                    LOGGER.error("An error occurred while sending AIRTIME email to address : " + user1.getEmail()
                            + "; error: " + ex.getMessage());
                }

            }

        }

        return response.getBody();
    }

}
