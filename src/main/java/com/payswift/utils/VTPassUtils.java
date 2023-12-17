package com.payswift.utils;

import lombok.RequiredArgsConstructor;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

@RequiredArgsConstructor
public class VTPassUtils {


    public static final String PUBLIC_KEY = "PK_3943a264f51f16f37748a48b64b052687cc252447c1";
    public static final String SECRETE_KEY = "SK_3078e74e7770ae273c7a976499cdab49b881dc830cd";

    public final static String PAY_BILL = "https://sandbox.vtpass.com/api/pay";

    public final static String BUY_DATA = "https://sandbox.vtpass.com/api/pay";
    public final static  String QUERY_TRANSACTION_STATUS= "https://sandbox.vtpass.com/api/requery";
    public static final String CONFIRM_DATA ="https://sandbox.vtpass.com/api/requery";


    public final static String GET_VARIATION_CODES = "https://sandbox.vtpass.com/api/service-variations?serviceID=";

    public static final String API_KEY = "4a8134a5a083801a759bd43347d808b1";


    public static String generateRequestId() {
        // Get current date and time in Africa/Lagos timezone
        TimeZone lagosTimeZone = TimeZone.getTimeZone("Africa/Lagos");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        dateFormat.setTimeZone(lagosTimeZone);
        String formattedDateTime = dateFormat.format(new Date());
        // Concatenate date and random string
        String requestId = formattedDateTime + VTPassUtils.generateRandomAlphabets();

        return requestId;
    }

    public static String generateRandomAlphabets() {
        StringBuilder randomStringBuilder = new StringBuilder(4);
        SecureRandom secureRandom = new SecureRandom();
        String alphabets = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 4; i++) {
            int randomIndex = secureRandom.nextInt(alphabets.length());
            randomStringBuilder.append(alphabets.charAt(randomIndex));
        }
        return randomStringBuilder.toString();
        //   Generate a random alphanumeric string (at least 12 characters)


    }

}



