package org.bcs;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

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
    
    
    public KeyPair generateKeyPair(){
        
        KeyPair pair = null;
        
        try {
            
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            pair = keyGen.generateKeyPair();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }    
           
        
//            PrivateKey priv = pair.getPrivate();
//            PublicKey pub = pair.getPublic();
            
        return pair;
        
    }
    
    public byte[] criptografa(String texto, PublicKey chave) {
        byte[] cipherText = null;
     
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            cipherText = cipher.doFinal(texto.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
     
        return cipherText;
    }
  
    /**
     * Decriptografa o texto puro usando chave privada.
     */
    public String decriptografa(byte[] texto, PrivateKey chave) {
        byte[] dectyptedText = null;
     
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            // Decriptografa o texto puro usando a chave Privada
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);
  
        } catch (Exception ex) {
            ex.printStackTrace();
        }
  
        return new String(dectyptedText);
    }
    
    
    
}
