package org.bcs;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Ballot implements Serializable{
    
//    public PublicKey nodeId;
    public List<PublicKey> nodesSignature = new ArrayList<>();
    public List<String> nodesVotes = new ArrayList<>();
    public String blockId;
//    public String vote;
    public int nodes;
    
    public Ballot(String blockId, int nodes){
//        this.nodeId = nodeId;
        this.blockId = blockId;
//        this.vote = vote;
        this.nodes = nodes;
    }
    
    public void vote(PublicKey nodeId, String vote){
        nodesSignature.add(nodeId);
        nodesVotes.add(vote);
    }
    
    public boolean hasVoted(PublicKey nodeId, String vote){
        for(int i=0;i<nodesSignature.size();i++){
            if(nodesSignature.get(i).equals(nodeId) && nodesVotes.get(i).equals(vote)){
                return true;
            }
        }
        
        return false;
        
    }
    
    public boolean ballotCanvass(String voteType){
        
        int votes = 0;
        int accepts = 0;
        
        for(int i=0;i<nodesVotes.size();i++){
            if(nodesVotes.get(i).equals("_vote_")){
                votes++;
            }else{
                accepts++;
            }
        }
        
        if(accepts >= ((nodes/3)*2)){
            return true;
        }else if(votes >= ((nodes/3)*2)){
            return true;
        }
        return false;
    }
    
}
