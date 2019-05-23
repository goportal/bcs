package org.bcs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Portal
 */
public class Node {
    
//    private List<Block> underVote = new ArrayList<>();
//    private List<Ballot> ballots = new ArrayList<>();
    
    Utils utils = new Utils();
    
    KeyPair keypair = utils.generateKeyPair();
    String nodeId = this.getStringFromKeys(keypair.getPublic());
    
    Chain blockchain = new Chain();
//    Server cServer = new Server();
    P2p p2p = new P2p(this);
    
//    Thread server = new Thread(cServer);
    Scanner sysIn = new Scanner(System.in);
    
    
//    private List<String> qSlices = new ArrayList<>();
    
    public void run() {
        
        boolean run = true;
        
        do {
            System.out.println("Choose the option:");
            System.out.println(" 0 - Add a new block");
            System.out.println(" 1 - Print the blockchain");
            System.out.println(" 2 - Validate chain");
//            System.out.println(" 3 - Check for blockchain updates");
            System.out.println(" 3 - Tamper the chain");
            System.out.println(" 4 - Exit");
//            System.out.println(" 6 - Start a server");
            System.out.println(" 5 - Connect to peer");
            System.out.println(" 6 - List connected nodes");
            System.out.println(" 7 - Print unvalidated blocks");
            System.out.println(" 8 - Start Voting");

            String opAux = sysIn.nextLine();
            
            int option;
            
            if(opAux == null || opAux.equals("") || !isNumber(opAux)){
                option = -1;
            }else{
                option = Integer.parseInt(opAux);
            }
            
            
          

switch (option) {
    case 0:
        System.out.println("What content do you want to put in the block?");
        String data = sysIn.nextLine();
        
        int index = blockchain.getIndex();
        String previousHash = blockchain.getCanonicalHash();
        Timestamp timest = new Timestamp(System.currentTimeMillis());
        String hash = utils.SHA256("" + index + previousHash + timest + data);
        
        Block newBlock = new Block(index, previousHash, hash, timest, data);
        
        this.ether.add(newBlock);
        
        p2p.publish(newBlock);
        
        break;
        
    case 1:
        System.out.println("The blockchain: ");
        blockchain.printChain();
        break;
        
    case 2:
        if (blockchain.isValid()) {
            System.out.println("The blockchain is valid!");
        } else {
            System.out.println("The blockchain is invalid!");
        }
        break;
        
    case 3:
        
        System.out.println("Which Block?");
        int nBlock = Integer.parseInt(sysIn.nextLine());
        
        System.out.println("Insert the new content:");
        String newContent = sysIn.nextLine();
        
        blockchain.tamper(nBlock, newContent);
        
        break;
        
    case 4:
        System.exit(0);
        
        run = false;
        break;

        
    case 5:
        System.out.println("Which ip:");

        p2p.connect(sysIn.nextLine());
        break;
        
    case 6:
        List<String> nodesIp = p2p.getNodesIp();
        
        System.out.println("Connected nodes: ");
        for (int i = 0; i < nodesIp.size(); i++) {
            System.out.println(nodesIp.get(i));
        }
        
        break;
        
    case 7:
        
//                    KeyPair keys = this.utils.generateKeyPair();
//
//                    PublicKey pubKey = keys.getPublic();
//                    PrivateKey privKey = keys.getPrivate();
//
//                    String content = "This is a text";
//
//                    System.out.println("plain text is: "+ content);
//
//                    byte[] cyText = utils.criptografa(content, pubKey);
//
//                    System.out.println("The encripted text is: "+cyText.toString());
//
//                    String decripted = utils.decriptografa(cyText, privKey);
//
//                    System.out.println("The decripted text is: "+decripted);
        
        for (int i = 0; i < ether.size(); i++) {
            System.out.println("Ether block: "+ether.get(i).getData());
        }
        
        
        break;
        
    case 8:
        
        startBallot();
        
        break;
        
    case 9:
        
        canvasVotes();
        
        
//        Ballot ballotin = new Ballot(ether.get(0).getHash(), 4);
//        Ballot ballotin2 = new Ballot(ether.get(0).getHash(), 4);
//        
//        KeyPair keypair1 = utils.generateKeyPair();
//        String nodeId1 = getStringFromKeys(keypair1.getPublic());
//        
//        KeyPair keypair2 = utils.generateKeyPair();
//        String nodeId2 = getStringFromKeys(keypair2.getPublic());
//        
//        KeyPair keypair3 = utils.generateKeyPair();
//        String nodeId3 = getStringFromKeys(keypair3.getPublic());
//        
//        KeyPair keypair4 = utils.generateKeyPair();
//        String nodeId4 = getStringFromKeys(keypair4.getPublic());
//        
//        ballotin.vote(nodeId1, "_vote_");
//        ballotin.vote(nodeId2, "_vote_");
//        ballotin.vote(nodeId3, "_vote_");
//        ballotin.vote(nodeId4, "_vote_");
        
         
//        System.out.println("BALLOTIN 1");
//
//        ballotin.printBallot();
//        
//        
//        System.out.println("BALLOTIN 2");
//
//        ballotin2.printBallot();
//        
//        
//        ballotin.merge(ballotin2);
        
//        System.out.println("MERGED BALLOTS");
        
//        ballotin.printBallot();
        
        
         
//        ballotin.canvass("_vote_");
        
//        System.out.println(" canvas Vote: "+ballotin.canvass("_vote_"));
        
//        System.out.println(" NODE2 has voted: "+ballotin.hasVoted(nodeId2, "_vote_"));
        
        
//        ballotin.hasVoted(nodeId1, "_vote_");
//        
//        ballotin.hasVoted(nodeId2, "_vote_");
        
        
//        ballotin.canvass("vote");
        
        
        break;
        
    default:
        System.out.println("Wrong choice! try again.");
        break;
        
}

        } while (run);
        
    }
    
    private List<Block> ether = new ArrayList<>();
//    private List<String> acceptedBlocks = new ArrayList<>();
    private Ballot ballot;
    
    public void consensus(Block block){
        if(!this.etherHasBlock(block)){
//            System.out.println("");
            ether.add(block);
            p2p.publish(block);
        }
    }
    
    public int TOTAL_NODES = 4;
    
    public void startBallot(){
        
        System.out.println("STARTING NEW BALLOT");
        
        if(!ether.isEmpty()){
            if(!blockchain.haveBlock(ether.get(0).getHash())){
                
                FileWriter fileWriter = null;
                try {
                    System.out.println("VOTED");
                    fileWriter = new FileWriter("../../voting.txt", true);
                    fileWriter.write("node:"+ether.get(0)+"\n");
                    fileWriter.write("_vote_:"+this.nodeId+"\n");
                    fileWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                }

                ballot = new Ballot(ether.get(0).getHash(),TOTAL_NODES);
                localVoted.add(ballot.blockId);
                p2p.publish(ballot);
                    
            }
        }
//        this.p2p.publish();
    }
    
//    boolean alreadyAccepted = false;
    
    
    public List<String> localVoted = new ArrayList<>();
    public List<String> localAccepted = new ArrayList<>();
    
    
    
    public void receiveBallot(Ballot tempBallot){
        
//                ballot.vote(this.nodeId,"_vote_");
//                localVoted.add(ballot.blockId);
//                System.out.println("not contains: "+!localVoted.contains(tempBallot));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(!localVoted.contains(tempBallot.blockId)){
                    localVoted.add(tempBallot.blockId);
                    p2p.publish(tempBallot);
                    
                    FileWriter fileWriter = null;
                    
                    try {
                        System.out.println("VOTED");
                        fileWriter = new FileWriter("../../voting.txt", true);
                        fileWriter.write("_vote_:"+this.nodeId+"\n");
                        fileWriter.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
//                 && !alreadyConsensed
                if(this.canvasVotes()){
                    tempBallot.voted = true;
                    System.out.println("CONSENSUS REACHED");
                }
                if(!tempBallot.voted){
                    p2p.publish(tempBallot);
                }
                
                
        
//            if(tempBallot.canvass("_vote_")){
//                System.out.println("REACHED CONSENSUS");
//            }else{
//                System.out.println("111111111111111");
//                if(localVoted.isEmpty() || !localVoted.contains(tempBallot.blockId)){
//                    System.out.println("222222222222222");
//                    tempBallot.vote(nodeId, "_vote_");
////                    System.out.println("4444444444444444: "+tempBallot.blockId);
//                    localVoted.add(tempBallot.blockId);
////                    System.out.println("555555555555555555");
//                }
//                System.out.println("33333333333333333");
//                p2p.publish(tempBallot);
//            }
            
        
    
//        System.out.println("RECEIVED BALLOT");
//        
//        boolean consensus = false;
//        
//        if(!localVoted.contains(tempBallot.blockId)){
//            System.out.println("VOTING BALLOT");
//            tempBallot.vote(nodeId, "_vote_");
//            localVoted.add(tempBallot.blockId);
//            if(ballot == null){
//                ballot = tempBallot;
//            }else{
//                ballot.merge(tempBallot);
//            }
//            
//        }else{
//            if(tempBallot.canvass("_vote_")){
//                consensus= true;
//                System.out.println("CONSENSUS REACHED");
//            }
//            System.out.println("");
//            if(ballot == null){
//                ballot = tempBallot;
//            }else{
//                ballot.merge(tempBallot);
//            }
//            
//        }
//        if(!consensus){
//            p2p.publish(ballot);
//        }
        
        
//        System.out.println("1111111111111111111");
//        
////        if(!this.ballot.nodesSignature.equals(tempBallot.nodesSignature)){
//            if(this.ballot == null || !tempBallot.blockId.equals(ballot.blockId)){
//                System.out.println("222222222222222222223");
//                this.ballot = tempBallot;
//            }
////            else{
////                ballot.merge(tempBallot);
////            }
//
//
//            if(!blockchain.haveBlock(ether.get(0).getHash())){
//                System.out.println("3333333333333333333");
//                if(tempBallot.canvass("_vote_")){
//                    System.out.println("4444444444444444444");
//                    if(tempBallot.canvass("_accept_")){
//                        System.out.println("5555555555555555");
//                        this.blockchain.addBlock(ether.get(0));
//                        ether.remove(ether.get(0));
//                    }else{
//                        System.out.println("6666666666666666");
//                        if(!tempBallot.hasVoted(nodeId, "_accept_") && !localAccepted.contains(tempBallot.blockId)){
//                            System.out.println("77777777777777777777");
//                            tempBallot.vote(nodeId, "_accept_");
//                            
////                            ballot.merge(tempBallot);,
//                            localAccepted.add(tempBallot.blockId);
//                            ballot = tempBallot;
//                            p2p.publish(ballot);
//                            
//                        }
//                        
//                        if(!this.ballot.equalsBallot(tempBallot)){
////                            ballot.merge(tempBallot);
//                            ballot = tempBallot;
//                            p2p.publish(ballot);
//                        }
//                        
//    //                    this.ballot = tempBallot;
//                        
//                    }
//                }else{
//    //                System.out.println("nodeid: "+this.nodeId);
//    //                System.out.println("");
//                    System.out.println("88888888888888888888 ===== "+ !tempBallot.hasVoted(nodeId, "_vote_"));
//                    if(!tempBallot.hasVoted(nodeId, "_vote_") && !localVoted.contains(tempBallot.blockId)){
//                        System.out.println("999999999999999999");
//                        tempBallot.vote(nodeId, "_vote_");  
//                        
////                        ballot.merge(tempBallot);
//                        localVoted.add(tempBallot.blockId);
//                        ballot = tempBallot;
//                        p2p.publish(ballot);
//                    }
//                    if(!this.ballot.equalsBallot(tempBallot)){
//                        System.out.println("EEEEEEEEEEEEEEEEEE");
////                        ballot.merge(tempBallot);
//                        ballot = tempBallot;
//                        p2p.publish(ballot);
//                    }
//    //                this.ballot = tempBallot;
//                    
//                }
//
//            }
//        }
        
        /////////////////////////////////////////////////////1
        
//        if(!ballot.hasVoted(nodeId, "_vote_")){
//            ballot.vote(nodeId,"_vote_");
//        }
//        
//        
//        if(this.ballot != null){
//            if(!tempBallot.blockId.equals(ballot.blockId)){
//                this.ballot = tempBallot;
//            }
//        }
//        
//        
//        System.out.println("Received a ballot");
//        
//        if(!tempBallot.hasVoted(nodeId,tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
//            System.out.println("Ballot not voted");
//            if(!blockchain.haveBlock(tempBallot.blockId)){
////                if(!this.alreadyAcceptedBlock(tempBallot.blockId)){
////                    System.out.println("ALREADY ACCEPTED: "+this.alreadyAcceptedBlock(tempBallot.blockId));
//                if(!alreadyAccepted){
//                    System.out.println("Voting in ballot: "+tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1));
//                    tempBallot.vote(this.nodeId,tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1));
//                    this.ballot = tempBallot;
//                    this.p2p.publish(ballot);
//                }
////                }
//            }
//            if(ballot.canvass(tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
//                System.out.println("BALLOT REACH CONSENSUS");
//                
//                if(!ballot.hasVoted(this.nodeId,"_accept_")){
//                    System.out.println("Voting to _accept_");
//                    this.ballot.vote(this.nodeId,"_accept_");
//                    alreadyAccepted = true;
////                    this.markAcceptedBlock(tempBallot.blockId);
//                    this.p2p.publish(ballot);
////                    startBallot("_accept_");
//                }else{
//                    if(!blockchain.haveBlock(tempBallot.blockId)){
//                        System.out.println("ADDING TO THE BLOCKCHAIN ");
//                        blockchain.addBlock(blockFromBallot(tempBallot));
//                        alreadyAccepted = false;
////                        this.ballot = null;
//                    }
//                }
//            }
//        }else{
//            System.out.println("Receiving already voted ballot");
//            if(tempBallot.nodesSignature.size()>this.ballot.nodesSignature.size()){
//                System.out.println("Updating local ballot");
//                this.ballot = tempBallot;
//                this.p2p.publish(ballot);
//            }
//            if(ballot.canvass(tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
//                System.out.println("BALLOT REACH CONSENSUS (by2)");
//                if(!ballot.hasVoted(this.nodeId,"_accept_")){
//                    System.out.println("Voting to _accept_ (by2)");
////                    tempBallot.vote(this.nodeId,"_accept_");
////                    this.markAcceptedBlock(tempBallot.blockId);
//                    alreadyAccepted = true;
//                    this.ballot.vote(this.nodeId,"_accept_");
//                    this.p2p.publish(ballot);
//
////                    startBallot("_accept_");
//                }else{
//                    if(!blockchain.haveBlock(tempBallot.blockId)){
//                        System.out.println("ADDING TO THE BLOCKCHAIN (by2)");
//                        blockchain.addBlock(blockFromBallot(tempBallot));
//                        alreadyAccepted = false;
////                        this.ballot = null;
//                    }
//                }
//            }
//        }
    }
    
    public boolean canvasVotes() {
        List<String> lines = new ArrayList<>();
        int counter = 0;
        try{
            File file = new File("../../voting.txt"); 
            Scanner sc = new Scanner(file); 

            while (sc.hasNextLine()){
                counter++;
                lines.add(sc.nextLine());
            }
            
            if(counter-1 >= (TOTAL_NODES/3)*2){
                return true;
            }
            
        }catch(Exception X){
            System.out.println("Error: "+X.getMessage());
        }
        return false;
    }
    
    public Block blockFromBallot(Ballot tempBallot){
        Block tempBlock = null;
        for(int i=0;i<this.ether.size();i++){
            if(ether.get(i).getHash().equals(tempBallot.blockId)){
                tempBlock = ether.get(i);
                ether.remove(i);
            }
        }
        return tempBlock;
    }
    
    public boolean etherHasBlock(Block tempBlock){
        for (int i = 0; i < this.ether.size(); i++) {
            if(this.ether.get(i).getHash().equals(tempBlock.getHash())){
//                System.out.println("HASH 1 => "+this.ether.get(i).getHash());
//                System.out.println("HASH 2 => "+tempBlock.getHash());
                return true;
            }
        }
        return false;
    }
    
//    public boolean alreadyAcceptedBlock(String tempBlockId){
//        for (int i = 0; i < this.acceptedBlocks.size(); i++) {
//            if(tempBlockId.equals(acceptedBlocks.get(i))){
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    public void markAcceptedBlock(String tempBlockId){
//        this.acceptedBlocks.add(tempBlockId);
//    }
//   
    public boolean isNumber( String s ) {
        
        char[] c = s.toCharArray();
        boolean d = true;
        for ( int i = 0; i < c.length; i++ )
            
            if ( !Character.isDigit( c[ i ] ) ) {
                d = false;
                break;
            }
        return d;
    }
    
    public String getStringFromKeys(PublicKey tempKey){
        byte[] publicKey = tempKey.getEncoded();
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < publicKey.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (publicKey[i] & 0x00FF)).substring(1));
        }
//        System.out.println(retString);
        return retString.toString();
    }
    
}


// public void startBallot(String type){
//        
//        System.out.println("Started new Ballot");
//        if(!ether.isEmpty()){
//            if(!blockchain.haveBlock(ether.get(0).getHash())){
//                System.out.println("Valid ballot");
//                ballot = new Ballot(ether.get(0).getHash(),TOTAL_NODES);
//                
//                if(!ballot.hasVoted(nodeId,type)){
//                    System.out.println("Voting in new Ballot");
//                    ballot.vote(this.nodeId,type);
//                    p2p.publish(ballot);
//                }
//            }
//        }
////        this.p2p.publish();
//    }
//    
//    boolean alreadyAccepted = false;
//    
//    public void ballotConsensus(Ballot tempBallot){
//        
//        
//        if(this.ballot != null){
//            if(!tempBallot.blockId.equals(ballot.blockId)){
//                this.ballot = tempBallot;
//            }
//        }
//        
//        
//        System.out.println("Received a ballot");
//        
//        if(!tempBallot.hasVoted(nodeId,tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
//            System.out.println("Ballot not voted");
//            if(!blockchain.haveBlock(tempBallot.blockId)){
////                if(!this.alreadyAcceptedBlock(tempBallot.blockId)){
////                    System.out.println("ALREADY ACCEPTED: "+this.alreadyAcceptedBlock(tempBallot.blockId));
//                if(!alreadyAccepted){
//                    System.out.println("Voting in ballot: "+tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1));
//                    tempBallot.vote(this.nodeId,tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1));
//                    this.ballot = tempBallot;
//                    this.p2p.publish(ballot);
//                }
////                }
//            }
//            if(ballot.ballotCanvass(tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
//                System.out.println("BALLOT REACH CONSENSUS");
//                
//                if(!ballot.hasVoted(this.nodeId,"_accept_")){
//                    System.out.println("Voting to _accept_");
//                    this.ballot.vote(this.nodeId,"_accept_");
//                    alreadyAccepted = true;
////                    this.markAcceptedBlock(tempBallot.blockId);
//                    this.p2p.publish(ballot);
////                    startBallot("_accept_");
//                }else{
//                    if(!blockchain.haveBlock(tempBallot.blockId)){
//                        System.out.println("ADDING TO THE BLOCKCHAIN ");
//                        blockchain.addBlock(blockFromBallot(tempBallot));
//                        alreadyAccepted = false;
////                        this.ballot = null;
//                    }
//                }
//            }
//        }else{
//            System.out.println("Receiving already voted ballot");
//            if(tempBallot.nodesSignature.size()>this.ballot.nodesSignature.size()){
//                System.out.println("Updating local ballot");
//                this.ballot = tempBallot;
//                this.p2p.publish(ballot);
//            }
//            if(ballot.ballotCanvass(tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
//                System.out.println("BALLOT REACH CONSENSUS (by2)");
//                if(!ballot.hasVoted(this.nodeId,"_accept_")){
//                    System.out.println("Voting to _accept_ (by2)");
////                    tempBallot.vote(this.nodeId,"_accept_");
////                    this.markAcceptedBlock(tempBallot.blockId);
//                    alreadyAccepted = true;
//                    this.ballot.vote(this.nodeId,"_accept_");
//                    this.p2p.publish(ballot);
//
////                    startBallot("_accept_");
//                }else{
//                    if(!blockchain.haveBlock(tempBallot.blockId)){
//                        System.out.println("ADDING TO THE BLOCKCHAIN (by2)");
//                        blockchain.addBlock(blockFromBallot(tempBallot));
//                        alreadyAccepted = false;
////                        this.ballot = null;
//                    }
//                }
//            }
//        }
//    }