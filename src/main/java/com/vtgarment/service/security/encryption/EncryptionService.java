package com.vtgarment.service.security.encryption;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncryptionService implements Serializable {

    public static String encryption(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.digest(password.getBytes(Charset.forName("UTF-8")))
            byte[] messageDigest = md.digest(password.getBytes(Charset.forName("UTF-8")));
            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
