package org.bcs;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 *
 * @author Portal
 */

public class Block implements Serializable{
    private int index;
    private String previousHash;
    private String hash;
    private Timestamp timestamp;
    private String data;
//    private String signature;

    public Block(int index, String previousHash, String hash, Timestamp timestamp, String data){
        this.index = index;
        this.previousHash = previousHash;
        this.hash = hash;
        this.timestamp = timestamp;
        this.data = data;
    }
    
    public String getHash(){
        return hash;
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



