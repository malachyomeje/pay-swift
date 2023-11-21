package com.payswift.paystack;

import com.payswift.model.Users;
import com.payswift.model.Wallet;
import com.payswift.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.utils.PayStackUtils.CREATE_ACCOUNT;
import static com.payswift.utils.PayStackUtils.SECRET_KEY;

@Service
@RequiredArgsConstructor
public class PayStackServiceImp implements PayStackService {
    private final UsersRepository usersRepository;
   // private  final WalletRepository walletRepository;


    @Override
    public VirtualAccountResponse createAccount(Users users4) {


        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()) {
            return new VirtualAccountResponse("User not found ");
        }

//        Optional<Users> users1 = usersRepository.findByEmail(users.getEmail());
//        if (users1.isEmpty()) {
//            return new VirtualAccountResponse("User not found ");
//        }
        Users users3 = users.get();


        Wallet wallet = users3.getUserWallet();


        VirtualAccountDto virtualAccountDto = new VirtualAccountDto();
        virtualAccountDto.setEmail(users3.getEmail());
        virtualAccountDto.setFirstName(users3.getFirstName());
        virtualAccountDto.setMiddleName(users3.getMiddleName());
        virtualAccountDto.setLastName(users3.getLastName());
        virtualAccountDto.setPhone(users3.getPhoneNumber());
        virtualAccountDto.setCountry(users3.getCountry());
        virtualAccountDto.setPreferredBank(wallet.getAccountNumber());
       // virtualAccountDto.setPreferredBank(CREATE_ACCOUNT);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + SECRET_KEY);


        System.out.println("virtual header");

        System.out.println(headers);

        HttpEntity<VirtualAccountDto> entity = new HttpEntity<>(virtualAccountDto, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<VirtualAccountResponse> response = restTemplate.exchange(CREATE_ACCOUNT, HttpMethod.POST, entity, VirtualAccountResponse.class);

        if (response.getBody() != null && response.getBody().isStatus()) {
            // Update the virtual account information in the Users entity
            users3.setAccountNumber(response.getBody().getMessage());


        System.out.println("virtual response");
        System.out.println(response);

        System.out.println(response);

        if (!response.getBody().isStatus())
            return new VirtualAccountResponse("Request failed");
        return new VirtualAccountResponse(true,"SUCCESSFUL");


    }

}
