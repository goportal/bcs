package org.bcs;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Portal
 */
public class Node {
    
//    private List<Block> underVote = new ArrayList<>();
//    private List<Ballot> ballots = new ArrayList<>();
    
    Utils utils = new Utils();
    
    KeyPair keypair = utils.generateKeyPair();
    PublicKey nodeId = keypair.getPublic();
    
    Chain blockchain = new Chain();
//    Server cServer = new Server();
    P2p p2p = new P2p(this);
    
//    Thread server = new Thread(cServer);
    Scanner sysIn = new Scanner(System.in);
    
    int doTest = 0;
    
//    private List<String> qSlices = new ArrayList<>();
    
    public void run() {
        
        doTest=1;
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


int option = Integer.parseInt(sysIn.nextLine());

switch (option) {
    case 0:
        System.out.println("What content do you want to put in the block?");
        String data = sysIn.nextLine();
        
        int index = blockchain.getIndex();
        String previousHash = blockchain.getCanonicalHash();
        Timestamp timest = new Timestamp(System.currentTimeMillis());
        String hash = utils.SHA256("" + index + previousHash + timest + data);
        
        Block newBlock = new Block(index, previousHash, hash, timest, data);
        
//                    blockchain.addBlock(newBlock);
        
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
        
//                case 3:
//                    System.out.println("Bueller?");
//                    break;
        
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
        
//                case 6:
//                    startServer();
//                    break;
        
    case 5:
        System.out.println("Which ip:");
//                    cServer.connect(sysIn.nextLine());
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
        
        startBallot("_vote_");
        
        break;
        
    default:
        System.out.println("Wrong choice! try again.");
        break;
        
}

        } while (run);
        
    }
    
//    public void connectToSlice(String sliceName){
//        this.qSlices.add(sliceName);
//    }
    
    private List<Block> ether = new ArrayList<>();
    private List<String> acceptedBlocks = new ArrayList<>();
    private Ballot ballot;
    
    public void consensus(Block block){
        if(!this.etherHasBlock(block)){
//            System.out.println("");
            ether.add(block);
            p2p.publish(block);
        }
    }
    
    public int TOTAL_NODES = 4;
    
    public void startBallot(String type){
        
        System.out.println("Started new Ballot");
        if(!ether.isEmpty()){
            if(!blockchain.haveBlock(ether.get(0).getHash())){
                System.out.println("Valid ballot");
                ballot = new Ballot(ether.get(0).getHash(),TOTAL_NODES);
                
                if(!ballot.hasVoted(nodeId,type)){
                    System.out.println("Voting in new Ballot");
                    ballot.vote(this.nodeId,type);
                    p2p.publish(ballot);
                }
            }
        }
//        this.p2p.publish();
    }
    
    boolean alreadyAccepted = false;
    
    public void ballotConsensus(Ballot tempBallot){
        
        
        if(this.ballot != null){
            if(!tempBallot.blockId.equals(ballot.blockId)){
                this.ballot = tempBallot;
            }
        }
        
        
        System.out.println("Received a ballot");
        
        if(!tempBallot.hasVoted(nodeId,tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
            System.out.println("Ballot not voted");
            if(!blockchain.haveBlock(tempBallot.blockId)){
//                if(!this.alreadyAcceptedBlock(tempBallot.blockId)){
//                    System.out.println("ALREADY ACCEPTED: "+this.alreadyAcceptedBlock(tempBallot.blockId));
if(!alreadyAccepted){
    System.out.println("Voting in ballot: "+tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1));
    tempBallot.vote(this.nodeId,tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1));
    this.ballot = tempBallot;
    this.p2p.publish(ballot);
}
//                }

            }
            if(ballot.ballotCanvass(tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
                System.out.println("BALLOT REACH CONSENSUS");
                
                if(!ballot.hasVoted(this.nodeId,"_accept_")){
                    System.out.println("Voting to _accept_");
                    this.ballot.vote(this.nodeId,"_accept_");
                    alreadyAccepted = true;
//                    this.markAcceptedBlock(tempBallot.blockId);
this.p2p.publish(ballot);
//                    startBallot("_accept_");
                }else{
                    if(!blockchain.haveBlock(tempBallot.blockId)){
                        System.out.println("ADDING TO THE BLOCKCHAIN ");
                        blockchain.addBlock(blockFromBallot(tempBallot));
                        alreadyAccepted = false;
//                        this.ballot = null;
                    }
                }
            }
        }else{
            System.out.println("Receiving already voted ballot");
            if(tempBallot.nodesSignature.size()>this.ballot.nodesSignature.size()){
                System.out.println("Updating local ballot");
                this.ballot = tempBallot;
                this.p2p.publish(ballot);
            }
            if(ballot.ballotCanvass(tempBallot.nodesVotes.get(tempBallot.nodesVotes.size()-1))){
                System.out.println("BALLOT REACH CONSENSUS (by2)");
                if(!ballot.hasVoted(this.nodeId,"_accept_")){
                    System.out.println("Voting to _accept_ (by2)");
//                    tempBallot.vote(this.nodeId,"_accept_");
//                    this.markAcceptedBlock(tempBallot.blockId);
alreadyAccepted = true;
this.ballot.vote(this.nodeId,"_accept_");
this.p2p.publish(ballot);

//                    startBallot("_accept_");
                }else{
                    if(!blockchain.haveBlock(tempBallot.blockId)){
                        System.out.println("ADDING TO THE BLOCKCHAIN (by2)");
                        blockchain.addBlock(blockFromBallot(tempBallot));
                        alreadyAccepted = false;
//                        this.ballot = null;
                    }
                }
            }
        }
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
    
    public boolean alreadyAcceptedBlock(String tempBlockId){
        for (int i = 0; i < this.acceptedBlocks.size(); i++) {
            if(tempBlockId.equals(acceptedBlocks.get(i))){
                return true;
            }
        }
        return false;
    }
    
    public void markAcceptedBlock(String tempBlockId){
        this.acceptedBlocks.add(tempBlockId);
    }
    
//    ArrayList< ArrayList > tempCounter = new ArrayList< ArrayList>();
    
//    public boolean ballotCanvass(){
//
//        int [] vetor = new int[this.ballots.size()];
//
//        for(int i=0;i<this.ballots.size();i++){
//
//        }
//
//        for(int i=0;i<this.ballots.size();i++){
//
//        }
//
//    }
    
    
//    public boolean alreadyVoted(Ballot vote){
//        for(int i=0;i<this.ballots.size();i++){
//            Ballot tempBallot = this.ballots.get(i);
//            if(tempBallot.blockId.equals(vote.blockId) && tempBallot.nodeId.equals(vote.nodeId) && tempBallot.vote.equals("_vote_")){
//                return true;
//            }
//        }
//        return false;
//    }
//
    
//    blockchain.addBlock(block);
    
//    TCP server
//    public void startServer() {
//        server.start();
//    }
    
}



//
// Block block;
//        Ballot ballot;
//
//        if(message instanceof Block){
//            block = (Block) message;
//
//            if(!blockchain.haveBlock(block.getHash())){
//                if(!underVote.contains(block)){
//                    underVote.add(block);
//                    Ballot vote = new Ballot(this.nodeId, block.getHash(),"_vote_");
//
//                    ballots.add(vote);
//
//                    p2p.publish(block);
//                    p2p.publish(vote);
//
//                }
//
//            }else{
//                if(ballots.size()>=3){
//                    this.blockchain.addBlock(block);
//                }
//            }
//
//        }