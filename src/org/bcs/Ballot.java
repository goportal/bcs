package org.bcs;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Ballot implements Serializable{
    
//    public PublicKey nodeId;
    public List<PublicKey> nodesSignature = new ArrayList<>();
    public String blockId;
    public String vote;
    public int nodes;
    
    public Ballot(String blockId, String vote, int nodes){
//        this.nodeId = nodeId;
        this.blockId = blockId;
        this.vote = vote;
        this.nodes = nodes;
    }
    
    public void vote(PublicKey nodeId){
        nodesSignature.add(nodeId);
    }
    
    public boolean hasVoted(PublicKey nodeId){
        return nodesSignature.contains(nodeId);
    }
    
    public boolean ballotCanvass(){
        if(nodesSignature.size()>= ((nodes/3)*2)){
            return true;
        }
        return false;
    }
    
}
