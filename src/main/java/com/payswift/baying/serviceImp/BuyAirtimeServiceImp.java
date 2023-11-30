package com.payswift.baying.serviceImp;

import com.payswift.bank.serviceImp.FlutterWaveImp;
import com.payswift.baying.request.BuyAirtimeDto;
import com.payswift.baying.response.BuyAirtimeResponse;
import com.payswift.baying.service.BuyAirtimeService;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
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

import static com.payswift.utils.VTPassUtils.*;



@Slf4j
@RequiredArgsConstructor
@Service
public class BuyAirtimeServiceImp implements BuyAirtimeService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(BuyAirtimeServiceImp.class);

    @Override
    public BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID){

        LOGGER.info("fetching  BuyAirtimeResponse");

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

        BuyAirtimeDto buyAirtimeDto = new BuyAirtimeDto();
     //   buyAirtimeDto.setRequest_id("2022013016104738492849");
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
        log.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        ResponseEntity<BuyAirtimeResponse> response = restTemplate.exchange
                (PAY_BILL, HttpMethod.POST, entity, BuyAirtimeResponse.class);

       return response.getBody();

    }
}
