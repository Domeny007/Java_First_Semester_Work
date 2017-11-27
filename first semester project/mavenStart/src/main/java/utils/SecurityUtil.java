package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtil {

    private final static String ALG = "MD5";

    private final static int COOKIE_LENGTH = 32;

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALG);
            md.update(password.getBytes(StandardCharsets.UTF_8));
            password = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }

    public static String generateRandomCookieId() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[COOKIE_LENGTH];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16);
    }

}
