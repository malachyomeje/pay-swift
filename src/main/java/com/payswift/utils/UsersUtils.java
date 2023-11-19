package com.payswift.utils;

import java.security.SecureRandom;

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
}