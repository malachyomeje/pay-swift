package com.payswift.baying.serviceImp;

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

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

import static com.payswift.utils.VTPassUtils.*;


@RequiredArgsConstructor
@Service
public class BuyAirtimeServiceImp implements BuyAirtimeService {

    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;

    @Override
    public BuyAirtimeResponse buyAirtime(String phone, Double amount, String serviceID){

        String userEmail = UsersUtils.getAuthenticatedUserEmail();
        // getAuthenticatedUserEmail();
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
        buyAirtimeDto.setPhone(phone);
        buyAirtimeDto.setAmount(amount);
        buyAirtimeDto.setServiceID(serviceID);
        buyAirtimeDto.setRequest_id(VTPassUtils.generateRequestId());


        HttpHeaders headers = new HttpHeaders();
        headers.set("api_key", API_KEY);
        headers.set("secret-key", SECRETE_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<BuyAirtimeDto> entity = new HttpEntity<>(buyAirtimeDto, headers);


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BuyAirtimeResponse> response = restTemplate.exchange
                (PAY_BILL, HttpMethod.POST, entity, BuyAirtimeResponse.class);

       return response.getBody();
    }
}
