package com.folm.bysj.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Sha_demo {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String passwordToHash = "password";

        byte[] salt = getSalt();
        String securePassword = get_SecurePassword(passwordToHash, salt, "SHA-1");
        System.out.println(securePassword);

        securePassword = get_SecurePassword(passwordToHash, salt, "SHA-256");
        System.out.println(securePassword);
        System.out.println(new BigInteger(securePassword, 16).toString());

        securePassword = get_SecurePassword(passwordToHash, salt, "SHA-384");
        System.out.println(securePassword);

        securePassword = get_SecurePassword(passwordToHash, salt, "SHA-512");
        System.out.println(securePassword);
    }

    private static String get_SecurePassword(String passwordToHash, byte[] salt, String name){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(name);
//            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());

            StringBuilder sb = new StringBuilder();

            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
