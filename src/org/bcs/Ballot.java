package org.bcs;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Ballot implements Serializable{
    
//    public PublicKey nodeId;
//    public int version = 0;
    
   
    
    public List<String> nodesAccepts = new ArrayList<>();
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
    
    public void vote(String nodeId, String vote){
        
        if(vote.equals("_vote_")){
//            System.out.println("VOTANDO A");
//            if(!nodesVotes.contains(nodeId)){
                nodesVotes.add(nodeId);
//            }
            
        }else{
//            System.out.println("VOTANDO B");
//            if(!nodesAccepts.contains(nodeId)){
                nodesAccepts.add(nodeId);
//            }
            
        }
        
    }
    
    public boolean hasVoted(String nodeId, String vote){
        
        if(vote.equals("_vote_")){
            if(nodesVotes.contains(nodeId)){
                return true;
            }
        }else{
            if(nodesAccepts.contains(nodeId)){
                return true;
            }
        }
        
        return false;
        
//        for(int i=0;i<nodesSignature.size();i++){
//            
////            System.out.println("nodes Signature: "+nodesSignature.get(i));
////            System.out.println("nodeid: "+nodeId);
//            
//            if(nodesSignature.get(i).equals(nodeId) && nodesVotes.get(i).equals(vote)){
//                return true;
//            }
//        }
//        
//        return false;
        
    }
    
    public boolean canvass(String voteType){
        
//        double votes = 0;
//        double accepts = 0;
//        
//        for(int i=0;i<nodesVotes.size();i++){
//            if(nodesVotes.get(i).equals("_vote_")){
//                votes++;
//            }else{
//                accepts++;
//            }
//        }
        
//        System.out.println("votes: "+votes);
//        System.out.println("accepts: "+accepts);
        
        
        
//        System.out.println("calcul: " + (aux/3)*2);
        

        double aux = nodes;
        if(voteType.equals("_vote_")){
//            double aux2 = nodesVotes.size();
            if(nodesVotes.size() >= ((aux/3)*2)){
                return true;
            }
        }else{
            if(nodesAccepts.size() >= ((aux/3)*2)){
                return true;
            }
        }
        
        return false;

    }
    
    public int qtdVotes(){
        return this.nodesVotes.size();
    }
    
    public int qtdAccepts(){
        return this.nodesAccepts.size();
    }
    
    public void merge(Ballot tempBallot){

        List<String> auxNodesVotes = this.nodesVotes;
         
        List<String> auxTempNodesVotes = tempBallot.nodesVotes;
        
        
        List<String> listTwoCopy = new ArrayList<>(auxTempNodesVotes);
        listTwoCopy.removeAll(auxNodesVotes);
        auxNodesVotes.addAll(listTwoCopy);
         
//        System.out.println(auxNodesSignature);
        
        this.nodesVotes = auxNodesVotes;
        
        
        List<String> auxNodesAccepts = this.nodesAccepts;
         
        List<String> auxTempNodesAccepts = tempBallot.nodesAccepts;
        
         
        List<String> listTwoCopy2 = auxTempNodesAccepts;
        listTwoCopy2.removeAll(auxNodesAccepts);
        auxNodesAccepts.addAll(listTwoCopy);
         
//        System.out.println(auxNodesSignature);
        
        this.nodesAccepts = auxNodesAccepts;
        
    }
    
//    public boolean equalsBallot(Ballot tempBallot){
//        
//        if(tempBallot.nodesSignature.size() != nodesSignature.size()){
//            return false;
//        }else{
//            for (int i = 0; i < tempBallot.nodesSignature.size(); i++) {
//                if(!nodesSignature.get(i).equals(tempBallot.nodesSignature.get(i))){
//                    return false;
//                }
//            }
//        }
//        
//        return true;
//        
//    }
    
    public void printBallot(){
        for (int i = 0; i < this.nodesVotes.size(); i++) {
//            System.out.println(i+" NODES ACCEPTS:  "+nodesAccepts.get(i));
            System.out.println(i+" NODES VOTES: "+nodesVotes.get(i));
            
        }
    }
//        
        
        
//        for (int i = 0; i < this.nodesSignature.size(); i++) {
//            tempBallot.nodesSignature.add(this.nodesSignature.get(i));
//            tempBallot.nodesVotes.add(this.nodesVotes.get(i));
//        }
//        
//        for (int i = 0; i < this.nodesSignature.size(); i++) {
//            for (int i2 = 0; i2 < tempBallot.nodesSignature.size(); i2++) {
//                if(this.nodesSignature.get(i).equals(tempBallot.nodesSignature.get(i2)) && this.nodesVotes.get(i).equals(tempBallot.nodesVotes.get(i2))){
//                    tempBallot.nodesSignature.remove(tempBallot.nodesSignature.get(i2));
//                    tempBallot.nodesVotes.remove(tempBallot.nodesVotes.get(i2));
//                }
//            }
//        }
//        
//        if(!tempBallot.nodesSignature.isEmpty()){
//            for (int i = 0; i < tempBallot.nodesSignature.size(); i++) {
//                this.nodesSignature.add(tempBallot.nodesSignature.get(i));
//                this.nodesVotes.add(tempBallot.nodesVotes.get(i));        
//            }
//        }
   
        
//    }
    
}
