package com.payswift.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class VTPassUtils {


    public static final String PUBLIC_KEY = "PK_858786fd2c69f59c6b2fc4ffc842b64f7a69ceea0ef";
    public static final String SECRETE_KEY = "SK_102196807d8c552d7884fb81ca499ce10e87b0d2a9a";

    public final static String PAY_BILL = "https://sandbox.vtpass.com/api/pay";

    public static final String API_KEY = " 4a8134a5a083801a759bd43347d808b1";


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
