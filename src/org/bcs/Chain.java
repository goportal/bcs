package org.bcs;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author portal
 */

//    int index, String previousHash, String hash, String data


public class Chain {

    Utils utils = new Utils();
    List<Block> chain = new ArrayList<>();

    public void Chain(){
        String hash = utils.SHA256("0"+"0"+"this is the genesis");
        Block genesis = new Block(0,"0",hash,"this is the genesis");
        chain.add(genesis);
    }
    
    public int getIndex(){
        return chain.size()+1;
    }
    
    public void addBlock(Block block) {
        chain.add(block);
    }
    
    public boolean isValid(){
        int sum = 0;
        for(int i=0;i<chain.size();i++){
           Block CB = chain.get(i);
           String CH = utils.SHA256(""+CB.getIndex()+CB.getTimestamp()+CB.getPreviousHash()+CB.getData());
           
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
    
}
