package com.tactfactory.my_qcm.configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Secure storage PWD in Mobile DB
 * Created by ProtoConcept GJ on 29/05/2016.
 */
public class Pwd {

    /**
     * Hashing password
     * @param password
     * @return byte[]
     */
    public static final byte[] sha512(final String password){
        try {
             MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
             final byte[] digest = messageDigest.digest(password.getBytes());
             return digest;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
        }

    /**
     * Convert byte to string
     * @param data
     * @return string
     */
    public static final String toHexString(byte[] data){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++){
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
