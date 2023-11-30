package com.payswift.utils;

import com.payswift.model.Users;
import com.payswift.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
public class UsersUtils {



    public static boolean validPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{5,}$";
        return password.matches(regex);
    }

    public static boolean validPhoneNumber(String phoneNumber) {
        String regex = "^(\\+234|0)[789]\\d{9}$";
        return phoneNumber.matches(regex);
    }

    public static String walletPin() {
        SecureRandom secureRandom = new SecureRandom();
        int UserWalletPin = secureRandom.nextInt(10000); // Generate a random 4-digit number

        return "PAY-SWIFT" + String.format("%04d", UserWalletPin);
    }
    public String generateSerialNumber(String prefix) {
        Random rand = new Random();
        long x = (long) (rand.nextDouble() * 100000000000000L);
        return prefix + String.format("%014d", x);
    }

    public static String getAuthenticatedUserEmail() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }


    }
