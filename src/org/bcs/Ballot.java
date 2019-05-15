package org.bcs;

import java.security.PublicKey;

public class Ballot {
    
    public PublicKey nodeId;
    public String blockId;
    public String vote;
    
    public Ballot(PublicKey nodeId, String blockId, String vote){
        this.nodeId = nodeId;
        this.blockId = blockId;
        this.vote = vote;
    }
}
