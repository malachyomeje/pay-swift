package com.payswift.service.serviceImp;

import com.payswift.bank.service.FlutterWaveService;
import com.payswift.config.JwtService;
import com.payswift.dtos.request.EmailDto;
import com.payswift.dtos.request.UpDatedUserDto;
import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.dtos.response.PagingAndSortingResponse;
import com.payswift.enums.Sex;
import com.payswift.enums.UserRole;

import com.payswift.exceptions.UserNotFoundException;
import com.payswift.model.Users;

import com.payswift.repository.UsersRepository;
import com.payswift.service.EmailService;
import com.payswift.service.UsersService;
import com.payswift.service.WalletService;
import com.payswift.utils.UsersUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersServiceImp implements UsersService {
    private  final UsersRepository usersRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final EmailService emailService;
    private final HttpServletRequest servletRequest;
    private  final WalletService walletService;
    private  final FlutterWaveService flutterWaveService;
    private final static Logger LOGGER = LoggerFactory.getLogger( UsersServiceImp.class);


    @Override
    public BaseResponse signUpUser (UsersDto usersDto){

        Optional<Users> users = usersRepository.findByEmail(usersDto.getEmail());
        if (users.isPresent()){
            return  new BaseResponse("USER ALREADY EXIST",usersDto.getEmail());
        }

        if (!UsersUtils.validPassword(usersDto.getPassword())) {
            return new BaseResponse<>("wrong password: must contain number," + " must contains alphabet," +
                    "and the length must not be less than 5");
        }
        if (!UsersUtils.validPhoneNumber(usersDto.getPhoneNumber())) {
            return new BaseResponse<>("Wrong PhoneNumber, Enter Correct PhoneNumber", usersDto.getPhoneNumber());
        }
       String token = jwtService.generateSignUpConfirmationToken(usersDto.getEmail());

        Users appUsers = Users.builder()
                .firstName(usersDto.getFirstName().toUpperCase())
                .lastName(usersDto.getLastName().toUpperCase())
                .middleName(usersDto.getMiddleName().toUpperCase())
                .email(usersDto.getEmail())
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .phoneNumber(usersDto.getPhoneNumber())
                .walletPin(usersDto.getWalletPin())
                .country(usersDto.getCountry())
                .isEmailVerified(false)
                .isLocked(false)
                .sex(Sex.valueOf(usersDto.getSex().name().toUpperCase()))
                .role(UserRole.USER)
               .confirmationToken(token)
                .build();
              usersRepository.save(appUsers);



        String http = servletRequest.getProtocol().substring(0,5).toLowerCase();
        if (!http.contains("s")) {
            http = http.substring(0,4);
        }

        String URL = http + "://"+servletRequest.getServerName()+":"+servletRequest.getServerPort()+"/api/v1/appUser/confirmRegistration?token=" + token;
        System.out.println(URL);
        String link = "<h3>Hello "  + appUsers.getFirstName()  +
                "<br> Click the link below to activate your account <a href=" + URL + "><br>Activate</a></h3>";

        System.out.println(link);

        String subject = "Pay-Swift Verification Code";

        EmailDto emailSenderDto = new EmailDto();
        emailSenderDto.setTo(appUsers.getEmail());
        emailSenderDto.setSubject(subject);
        emailSenderDto.setContent(link);
        emailService.sendEmail(emailSenderDto);
        return new BaseResponse<>("REGISTRATION SUCCESSFUL",
                "You have successful registered. Check your email to verify your account");
    }

@Override
    public BaseResponse confirmRegistration(String token) {
                Optional<Users> existingUser = usersRepository.findByConfirmationToken(token);
        if (existingUser.isEmpty()) {
            return new BaseResponse("User not found");
        }
            Users user = existingUser.get();
        LOGGER.info("{}",user);

           user.setConfirmationToken(user.getConfirmationToken());
            user.setEmailVerified(true);
            user.setLocked(true);
            usersRepository.save(user);
            flutterWaveService.createAccount(user.getEmail());
            walletService.registerWallet(user);

            return  new BaseResponse("Account verification successful",user);
        }
    @Override
    public PagingAndSortingResponse<List<Users>> usersSorting(String name) {
        List<Users> sortingUser = usersRepository.findAll(Sort.by(Sort.Direction.ASC, name));
        return new PagingAndSortingResponse<>(sortingUser.size(), sortingUser);
    }
    @Override
    public PagingAndSortingResponse<Page<Users>> usersPagination(int offset, int pageSize) {
        Page<Users> paging =usersRepository.findAll(PageRequest.of(offset, pageSize));
        return new PagingAndSortingResponse<>(paging.getSize(), paging);
    }
    @Override
    public PagingAndSortingResponse<Page<Users>> usersPaginationAndSorting(int offset, int pageSize, String name) {
        Page<Users> pagingAndSorting = usersRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(name)));
        return new PagingAndSortingResponse<>(pagingAndSorting.getSize(), pagingAndSorting);

    }

    @Override
    public List<UsersDto>findAllUsers(){
       List<Users>users = usersRepository.findAll();
       List<UsersDto> usersDto = new ArrayList<>();
       for (Users users1: users){

           UsersDto usersDto1 = UsersDto.builder()
                   .firstName(users1.getFirstName())
                   .middleName(users1.getMiddleName())
                   .lastName(users1.getLastName())
                   .email(users1.getEmail())
                   .sex(users1.getSex())
                   .accountNumber(users1.getAccountNumber())
                   .phoneNumber(users1.getPhoneNumber())
                   .build();
           usersDto.add(usersDto1);
       }
        return usersDto;
    }

    @Override
    public  BaseResponse findUserByEmail (String email){
       Optional<Users> users = usersRepository.findByEmail(email);
       if (users.isEmpty()){
           throw new UserNotFoundException("USER DOES NOT EXIST");
       }
       Users users1= users.get();
    return new BaseResponse("SUCCESSFUL",users1);
   }

@Override
    public BaseResponse lockUserAccount(String email) {
        Optional<Users> usersOptional = usersRepository.findByEmail(email);

        if (usersOptional.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }
        Users user = usersOptional.get();
        if (!user.isLocked()) {
            // If the account is not locked, lock it
            user.setLocked(true);
            return new BaseResponse("Account locked successfully", user);
        } else {
            // If the account is locked, unlock it
            user.setLocked(false);
            return new BaseResponse("Account unlocked successfully", user);
        }
    }

    @Override
    public  BaseResponse deleteUserAccount(String email){
        Optional<Users> users = usersRepository.findByEmail(email);
        if (users.isEmpty()){
            throw new UserNotFoundException("USER DOES NOT EXIST");
        }
        Users users1= users.get();
        usersRepository.delete(users1);
        return new BaseResponse("ACCOUNT DELETED SUCCESSFULLY",users1);
    }
    @Override
    public  BaseResponse updateUser(UpDatedUserDto upDatedUserDto){

        Optional<Users> users = usersRepository.findById(upDatedUserDto.getId());
        if (users.isEmpty()){
            throw new UserNotFoundException("USER DOES NOT EXIST");
        }
        Users users1= users.get();
        users1.setFirstName(users1.getFirstName());
        users1.setLastName(upDatedUserDto.getLastName());
        users1.setEmail(upDatedUserDto.getEmail());
        users1.setPhoneNumber(upDatedUserDto.getPhoneNumber());
        users1.setCountry(upDatedUserDto.getCountry());
        users1.setSex(upDatedUserDto.getSex());
        users1.setMiddleName(upDatedUserDto.getMiddleName());
        usersRepository.save(users1);

        return new BaseResponse("USER UPDATED SUCCESSFULLY",users1);
    }

}


