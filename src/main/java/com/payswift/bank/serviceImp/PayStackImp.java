package com.payswift.bank.serviceImp;

import com.payswift.bank.bankDtos.request.PayStackRequestDto;
import com.payswift.bank.bankDtos.response.PayStackResponse;
import com.payswift.bank.service.PayStackService;
import com.payswift.model.Users;
import com.payswift.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.utils.BankUtils.PAY_STACK_DEPOSIT;
import static com.payswift.utils.BankUtils.PAY_STACK_SECRET_KEY;

@Service
@RequiredArgsConstructor
public class PayStackImp implements PayStackService {

    private final UsersRepository usersRepository;


    @Override

    public ResponseEntity<String> payment(Double amount) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Users users1 = users.get();


    PayStackRequestDto payStackRequestDto = new PayStackRequestDto();
        payStackRequestDto.setEmail(users1.getEmail());
        payStackRequestDto.setAmount(users1.getUserWallet().getAmount()+amount);


    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);

    HttpEntity<PayStackRequestDto> entity = new HttpEntity<>(payStackRequestDto, headers);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<PayStackResponse> response = restTemplate.exchange
            (PAY_STACK_DEPOSIT, HttpMethod.POST, entity, PayStackResponse.class);

        if (response.getBody()== null)
            return new ResponseEntity<>("Request failed", HttpStatus.BAD_REQUEST);
        System.out.println("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
        System.out.println(response.getBody().getData().getAuthorizationUrl());
        return new ResponseEntity<>(response.getBody().getData().getAuthorizationUrl(), HttpStatus.ACCEPTED);


    }
}

