package com.payswift.service.serviceImp;

import com.payswift.dtos.request.BuyAirtimeDto;
import com.payswift.dtos.request.QueryTransactionResponseDto;
import com.payswift.dtos.response.BuyAirtimeResponse;
import com.payswift.dtos.response.QueryTransactionResponse;
import com.payswift.service.AirtimeService;
import com.payswift.enums.TransactionType;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Transaction;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.TransactionRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.utils.UsersUtils;
import com.payswift.utils.VTPassUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

import static com.payswift.enums.TransactionStatus.PENDING;
import static com.payswift.utils.VTPassUtils.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirtimeServiceImpl implements AirtimeService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(AirtimeServiceImpl.class);

    @Override
    public BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID){

        LOGGER.info("fetching  BuyAirtimeResponse");

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

        BuyAirtimeDto buyAirtimeDto = new BuyAirtimeDto();
        buyAirtimeDto.setServiceID(serviceID);
        buyAirtimeDto.setAmount(amount);
        buyAirtimeDto.setPhone(phone);
        buyAirtimeDto.setRequest_id(VTPassUtils.generateRequestId());

        log.info("Calling vtpass with entity: {}",buyAirtimeDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.set("public-key", PUBLIC_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuyAirtimeDto> entity = new HttpEntity<>(buyAirtimeDto, headers);
        LOGGER.info("Calling vtpass with entity: {}",entity);

        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling vtpass with entity: {}",entity);

        ResponseEntity<BuyAirtimeResponse> response = restTemplate.exchange
                (PAY_BILL, HttpMethod.POST, entity, BuyAirtimeResponse.class);

        Transaction walletTransaction = Transaction.builder()
                .name(users1.getFirstName() + " " + users1.getLastName())
                .wallet(userWallet)
                .transactionType(TransactionType.BUYAIRTIME)
                .transactionStatus(PENDING)
                .amount(buyAirtimeDto.getAmount())
                .transactionReference(buyAirtimeDto.getRequest_id())
                .build();

        transactionRepository.save(walletTransaction);

       return response.getBody();
    }

    @Override
   public QueryTransactionResponse confirmBuyAirtime(String request_id){

        LOGGER.info("entered confirmBuyAirtime");

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

    QueryTransactionResponseDto queryTransactionResponse = new QueryTransactionResponseDto();
    queryTransactionResponse.setRequest_id(request_id);

    HttpHeaders headers = new HttpHeaders();
    headers.set("api-key", API_KEY);
    headers.set("secret-key", SECRETE_KEY);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity< QueryTransactionResponseDto> entity = new HttpEntity<>(queryTransactionResponse, headers);

      RestTemplate restTemplate = new RestTemplate();
     LOGGER.info("Calling vtpass with entity: {}",entity);

     ResponseEntity<QueryTransactionResponse> response = restTemplate.exchange
             (QUERY_TRANSACTION_STATUS, HttpMethod.POST, entity,  QueryTransactionResponse.class);
        log.info("entered response: {}",response);
        return response.getBody();

}


}
