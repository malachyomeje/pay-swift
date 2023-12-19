package com.payswift.service.serviceImp;

import com.payswift.dtos.request.BuyElectricityDto;
import com.payswift.dtos.request.EmailDto;
import com.payswift.dtos.request.QueryElectricityTransactionDto;
import com.payswift.dtos.request.VerifyMeterNumberDto;
import com.payswift.dtos.response.BuyElectricityResponse;
import com.payswift.dtos.response.QueryElectricityTransactionResponse;
import com.payswift.dtos.response.VerifyMeterNumberResponse;
import com.payswift.enums.Description;
import com.payswift.enums.TransactionType;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Transaction;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.TransactionRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.service.ElectricityService;
import com.payswift.service.EmailService;
import com.payswift.utils.UsersUtils;
import com.payswift.utils.VTPassUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.enums.TransactionStatus.COMPLETED;
import static com.payswift.enums.TransactionStatus.PENDING;
import static com.payswift.utils.VTPassUtils.*;

@Service
@RequiredArgsConstructor
public class ElectricityServiceImp implements ElectricityService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private  final EmailService emailService;
    private final static Logger LOGGER = LoggerFactory.getLogger(ElectricityServiceImp.class);

    @Override
    public VerifyMeterNumberResponse  VerifyMeterNumber(VerifyMeterNumberDto verifyMeterNumberDto){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()){
            throw new UserNotFoundException("USER NOT FOUND");
        }

        VerifyMeterNumberDto verifyMeterNumberDto1 = new VerifyMeterNumberDto();
        verifyMeterNumberDto1.setBillersCode(verifyMeterNumberDto.getBillersCode());
        verifyMeterNumberDto1.setServiceID(verifyMeterNumberDto.getServiceID());
        verifyMeterNumberDto1.setType(verifyMeterNumberDto.getType());

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VerifyMeterNumberDto> entity = new HttpEntity<>(verifyMeterNumberDto1, headers);
        LOGGER.info("Calling vtpass with entity: {}", entity);

        RestTemplate restTemplate = new RestTemplate();
        LOGGER.info("Calling vtpass with entity: {}", entity);

        ResponseEntity<VerifyMeterNumberResponse> response = restTemplate.exchange
                (VERIFY_METER_NUMBER, HttpMethod.POST, entity, VerifyMeterNumberResponse.class);

        return response.getBody();
    }

    @Override
    public BuyElectricityResponse buyElectricity (BuyElectricityDto buyElectricityDto) {


        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Users> user = usersRepository.findByEmail(userEmail);
        if (user.isEmpty()){
            throw new UserNotFoundException("USER NOT FOUND");
        }
        Users users = user.get();

        Optional<Wallet> findUserWallet = walletRepository.findById(users.getUserWallet().getWalletId());
        if (findUserWallet.isEmpty()) {
            throw new WalletTransactionException("UserWallet not found");
        }
        Wallet userWallet = findUserWallet.get();

        if (userWallet.getAccountBalance() < Double.parseDouble(buyElectricityDto.getAmount())){
            throw new WalletTransactionException("INSUFFICIENT BALANCE,FUND-WALLET");
        }
        BuyElectricityDto buyElectricityDto1 = new BuyElectricityDto();
        buyElectricityDto1.setRequest_id(VTPassUtils.generateRequestId());
        buyElectricityDto1.setServiceID(buyElectricityDto.getServiceID());
        buyElectricityDto1.setBillersCode(buyElectricityDto.getBillersCode());
        buyElectricityDto1.setVariation_code(buyElectricityDto.getVariation_code());
        buyElectricityDto1.setAmount(buyElectricityDto.getAmount());
        buyElectricityDto1.setPhone(buyElectricityDto.getPhone());
        LOGGER.info("Entering buyDataDto with entity: {}", buyElectricityDto1);

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuyElectricityDto> entity = new HttpEntity<>(buyElectricityDto1, headers);
        LOGGER.info("Calling vtpass with entity: {}", entity);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BuyElectricityResponse> response = restTemplate.exchange
                (BUY_ELECTRICITY, HttpMethod.POST, entity, BuyElectricityResponse.class);

        Transaction walletTransaction = Transaction.builder()
                .name(users.getFirstName() + " " + users.getLastName())
                .wallet(userWallet)
                .description(Description.BUY_ELECTRICITY)
                .transactionType(TransactionType.DEBIT_ALERT)
                .transactionStatus(PENDING)
                .amount(Double.valueOf(buyElectricityDto.getAmount()))
                .transactionReference(buyElectricityDto1.getRequest_id())
                .build();
        transactionRepository.save(walletTransaction);

        return response.getBody();
    }

    @Override
    public QueryElectricityTransactionResponse confirmBuyElectricity(String request_id){

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

        QueryElectricityTransactionDto queryElectricityTransactionDto = new  QueryElectricityTransactionDto();
        queryElectricityTransactionDto .setRequest_id(request_id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<QueryElectricityTransactionDto> entity = new HttpEntity<>(queryElectricityTransactionDto, headers);

        RestTemplate restTemplate = new RestTemplate();
        LOGGER.info("Calling vtpass with entity: {}", entity);

        ResponseEntity<QueryElectricityTransactionResponse> response = restTemplate.exchange
                (QUERY_ELECTRICITY_TRANSACTION, HttpMethod.POST, entity, QueryElectricityTransactionResponse.class);

        if (response.getBody() != null) {
            if (response.getBody().getResponse_description().equals("TRANSACTION SUCCESSFUL")) {
                Transaction transaction1 = transaction3.get();
                transaction1.setTransactionStatus(COMPLETED);
                userWallet.setAccountBalance(userWallet.getAccountBalance()-transaction1.getAmount());

                transactionRepository.save(transaction1);
                walletRepository.save(userWallet);

                String subject = "Pay-Swift BUY-ELECTRICITY ";
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

