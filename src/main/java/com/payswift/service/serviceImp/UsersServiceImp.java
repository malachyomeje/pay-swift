package com.payswift.service.serviceImp;

import com.payswift.config.JwtService;
import com.payswift.dtos.request.EmailDto;
import com.payswift.dtos.request.UsersDto;
import com.payswift.dtos.response.BaseResponse;
import com.payswift.enums.Sex;
import com.payswift.enums.UserRole;
import com.payswift.model.Users;
import com.payswift.repository.UsersRepository;
import com.payswift.service.EmailService;
import com.payswift.service.UsersService;
import com.payswift.utils.UsersUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersServiceImp implements UsersService {
    private  final UsersRepository usersRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final EmailService emailService;
    private final HttpServletRequest servletRequest;


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
                .otherName(usersDto.getOtherName().toUpperCase())
                .email(usersDto.getEmail())
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .phoneNumber(usersDto.getPhoneNumber())
                .sex(Sex.valueOf(usersDto.getSex()))
                .role(UserRole.USER)
                .confirmationToken(token)
                .build();
        usersRepository.save(appUsers);

        String http = servletRequest.getProtocol().substring(0,5).toLowerCase();
        if (!http.contains("s")) {
            http = http.substring(0,4);
        }

        String URL = http + "://"+servletRequest.getServerName()+":"+servletRequest.getServerPort()+"api/v1/appUser/confirmRegistration/{token}" + token;

        String link = "<h3>Hello "  + appUsers.getFirstName()  +
                "<br> Click the link below to activate your account <a href=" + URL + "><br>Activate</a></h3>";

        String subject = "Pay-Pilot Verification Code";


        EmailDto emailSenderDto = new EmailDto();
        emailSenderDto.setTo(appUsers.getEmail());
        emailSenderDto.setSubject(subject);
        emailSenderDto.setContent(link);
        emailService.sendEmail(emailSenderDto);
        return new BaseResponse<>("REGISTRATION SUCCESSFUL",usersDto.getFirstName());
    }


@Override
    public BaseResponse confirmRegistration(String token) {
                Optional<Users> existingUser = usersRepository.findByConfirmationToken(token);
        if (existingUser.isEmpty()) {
            return new BaseResponse("User not found");
        }
            Users user = existingUser.get();
            user.setConfirmationToken(null);
            user.setIsEmailVerified(true);
            usersRepository.save(user);

            return  new BaseResponse("Account verification successful)");

        }
    }


