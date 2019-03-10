package org.bcs;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author portal
 */
public class Utils {

    public String SHA256(String rawText) {

        MessageDigest algorithm;
        byte text[] = "nothing".getBytes();
        
        try{
            algorithm = MessageDigest.getInstance("SHA-256");
            text = algorithm.digest(rawText.getBytes("UTF-8"));
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException X){
            System.out.println("ENCRYPTION ERROR: "+X);
        }
        
        StringBuilder hexText = new StringBuilder();
        for (byte b : text) {
            hexText.append(String.format("%02X", 0xFF & b));
        }
        String textCypher = hexText.toString();

        return textCypher;

    }
    
    

}
