package com.payswift.service.serviceImp;


import com.payswift.dtos.response.BuyDataVariationCodeResponse;
import com.payswift.exceptions.UserNotFoundException;
import com.payswift.exceptions.WalletTransactionException;
import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.UsersRepository;
import com.payswift.repository.WalletRepository;
import com.payswift.service.DataService;
import com.payswift.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.utils.VTPassUtils.*;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);


    @Override
    public BuyDataVariationCodeResponse DataVariationCode(String serviceID){
        LOGGER.info("ENTERED  DataServiceImpl");

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



}
