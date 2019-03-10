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
    
    private void addBlock(Block block) {
        chain.add(block);
    }

}
