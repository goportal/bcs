package org.bcs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author portal
 */

//    int index, String previousHash, String hash, String data


public class Chain {

    Utils utils = new Utils();
    private List<Block> chain = new ArrayList<>();

    public Chain(){
        Timestamp timest = new Timestamp(System.currentTimeMillis());
        String hash = utils.SHA256("0"+"0"+timest+"this is the genesis");
        Block genesis = new Block(0,"0",hash,timest,"this is the genesis");
        chain.add(genesis);
    }
    
    public int getIndex(){
        return chain.size();
    }
    
    public Block getBlock(int n){
        return chain.get(n);
    }
    
    public String getCanonicalHash(){
        return chain.get(chain.size()-1).getHash();
    }
    
    public void addBlock(Block block) {
        chain.add(block);
    }
    
    public boolean isValid(){
        int sum = 0;
        for(int i=0;i<chain.size();i++){
           Block CB = chain.get(i);
           String CH = utils.SHA256(""+CB.getIndex()+CB.getPreviousHash()+CB.getTimestamp()+CB.getData());

           if(!CB.getHash().equals(CH)){
               return false;
           }else{
               if(CB.getIndex()>0 && !chain.get(i-1).getHash().equals(CB.getPreviousHash())){
                   return false;
               }else{
                   sum++;
               }
           }
        }
        if(sum == chain.size()){
            return true;
        }else{
            return false;
        }
    }
    
    public void printChain(){
        for(int i=0;i<chain.size();i++){
            
            Block currentBlock = chain.get(i);
            
            System.out.println(" ---------------------------------------------------- ");
            System.out.println(" Index: "+currentBlock.getIndex());
            System.out.println(" Previous hash: "+currentBlock.getPreviousHash());
            System.out.println(" Hash: "+currentBlock.getHash());
            System.out.println(" Timestamp: "+currentBlock.getTimestamp().toString());
            System.out.println(" Data: "+currentBlock.getData());
            System.out.println(" ---------------------------------------------------- ");
            
        }
    }
    
    
//    This code snippet its just for research purpose, and should be removed to use.
    
    public void tamper(int nBlock, String newContent){
        
        String data = newContent;
        int index = nBlock;
        String previousHash = chain.get(nBlock).getPreviousHash();
        Timestamp timest = chain.get(nBlock).getTimestamp();
        String hash = chain.get(nBlock).getHash();

        Block newBlock = new Block(index, previousHash, hash, timest, data);

        chain.set(nBlock, newBlock);
        
    }
    
    
}
