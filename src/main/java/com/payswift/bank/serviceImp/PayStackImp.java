package com.payswift.bank.serviceImp;

import com.payswift.bank.bankDtos.request.PayStackRequestDto;
import com.payswift.bank.bankDtos.response.PayStackResponse;
import com.payswift.bank.service.PayStackService;
import com.payswift.model.Users;
import com.payswift.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger( PayStackImp.class);
    @Override
    public ResponseEntity<String> payment(String transactionType, Double amount) {

        LOGGER.info("ABOUT TO ENTER PAY_STACK");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Users users1 = users.get();
        LOGGER.info("getting the user from security context{} ",users1);

    PayStackRequestDto payStackRequestDto = new PayStackRequestDto();
        payStackRequestDto.setEmail(users1.getEmail());
        payStackRequestDto.setAmount(users1.getUserWallet().getAmount()+(amount*100));
       payStackRequestDto.setTransactionType(transactionType);


        LOGGER.info("creating pay_stack_dto{} ",payStackRequestDto);
    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + PAY_STACK_SECRET_KEY);

    HttpEntity<PayStackRequestDto> entity = new HttpEntity<>(payStackRequestDto, headers);
        LOGGER.info("getting the entity{} ",entity);

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<PayStackResponse> response = restTemplate.exchange
            (PAY_STACK_DEPOSIT, HttpMethod.POST, entity, PayStackResponse.class);
        LOGGER.info("getting the response{} ",response);
        if (response.getBody()== null)
           throw new RuntimeException("");
        LOGGER.info("getting the url{} ",response.getBody().getData().getAuthorizationUrl());
        System.out.println(response.getBody().getData().getAuthorizationUrl());
        return new  ResponseEntity(response.getBody().getData().getAuthorizationUrl(),HttpStatus.ACCEPTED);


    }
}

