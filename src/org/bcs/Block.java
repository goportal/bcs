/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//        self.index = index
//        self.previousHash = previousHash
//        self.timestamp = timestamp
//        self.data = data
//        self.signature = signature

// Timestamp timestamp = new Timestamp(System.currentTimeMillis());

package org.bcs;

import java.security.Timestamp;

/**
 *
 * @author Portal
 */

public class Block {
    private int index;
    private String previousHash;
    private String hash;
    private Timestamp timestamp;
    private String data;
//    private String signature;

    public Block(int index, String previousHash, String hash, String data){
        
    }
    
    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

//    public String getSignature() {
//        return signature;
//    }
}



