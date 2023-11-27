package com.payswift.utils;

import java.security.SecureRandom;

public class BankUtils {


    // FOR FLUTTER WAVE

    public static final String FLUTTER_WAVE_SECRET_KEY = "FLWSECK_TEST-b9ad33beb3ad3f27564326e1ab188542-X";

    public static final String FLUTTER_WAVE_VIRTUAL_ACCOUNT = "https://api.flutterwave.com/v3/virtual-account-numbers";


    // FOR PAY_STACK
    public static final String PAY_STACK_SECRET_KEY = "sk_test_4a7c7757dffdf7eb8c092437e67f651beb7ff016";

    public static String VM = "pk_live_f1849fd20261eded315df1997f0d44f257e47ef6";


    public static final String CALLBACK_URL = "http://localhost:8080/api/v1/wallet/verifyPayment/";


    public static final String PAY_STACK_VERIFY_TRANSACTION = "https://api.paystack.co/transaction/verify/";

    public static final String PAY_STACK_DEPOSIT = "https://api.paystack.co/transaction/initialize";


    public static String generateTransactionReference() {

                       SecureRandom secureRandom = new SecureRandom();
                int payNumber = secureRandom.nextInt(10000); // Generate a random 6-digit number

                return "PS" + String.format("%06d", payNumber);
        }
    }

