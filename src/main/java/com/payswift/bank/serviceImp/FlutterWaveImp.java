package com.payswift.bank.serviceImp;

import com.payswift.bank.bankDtos.request.FlutterWaveRequestDto;
import com.payswift.bank.bankDtos.response.FlutterWaveResponse;
import com.payswift.bank.service.FlutterWaveService;
import com.payswift.model.Bank;
import com.payswift.model.Users;
import com.payswift.repository.BankRepository;
import com.payswift.repository.UsersRepository;
import com.payswift.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.payswift.utils.BankUtils.FLUTTER_WAVE_SECRET_KEY;
import static com.payswift.utils.BankUtils.FLUTTER_WAVE_VIRTUAL_ACCOUNT;

@Service

@RequiredArgsConstructor
public class FlutterWaveImp implements FlutterWaveService {


    private  final UsersRepository usersRepository;
    private  final BankRepository bankRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(FlutterWaveImp.class);
    @Override
    public FlutterWaveResponse createAccount(String email) {
        LOGGER.info("ABOUT TO ENTER FLUTTER_WAVE");

//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Users users1 = users.get();

    FlutterWaveRequestDto flutterWaveRequestDto = new FlutterWaveRequestDto();
        flutterWaveRequestDto.setEmail(users1.getEmail());
        flutterWaveRequestDto.setAmount(1D);
        LOGGER.info("fetching flutterWaveDto{} ",flutterWaveRequestDto);

    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + FLUTTER_WAVE_SECRET_KEY);


        LOGGER.info("fetching headers{} ",headers);
    HttpEntity<FlutterWaveRequestDto > entity = new HttpEntity<>(flutterWaveRequestDto , headers);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity< FlutterWaveResponse > response = restTemplate.exchange
            (FLUTTER_WAVE_VIRTUAL_ACCOUNT, HttpMethod.POST, entity,   FlutterWaveResponse.class);

        LOGGER.info("request to Flutter_wave with payload{}",entity.getBody());


        LOGGER.info("ABOUT CRATE OBJECT OF BANK");
        Bank userBank = new Bank();
      //  userBankMapping.setBankName(response.getBody().getData().getBankName());
        BeanUtils.copyProperties(response.getBody().getData(),userBank);
     //   Bank bank = bankRepository.save(userBank);
        users1.setBank(userBank);
        users1.setAccountNumber(response.getBody().getData().getAccountNumber());
       bankRepository.save(userBank);
       usersRepository.save(users1);

       // usersRepository.save(users1);

        LOGGER.info("created account on flutter_wave and saved{}", userBank);


        System.out.println("Saved details");

        System.out.println(response);
        if (response.getBody()== null)
            throw new RuntimeException("user not found");
        LOGGER.info("creating the data{}", response.getBody().getData());
        return  new  FlutterWaveResponse (response.getBody().getStatus(),response.getBody().getMessage(),response.getBody().getData());
}

}


