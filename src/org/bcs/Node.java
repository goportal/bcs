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
    
    
String number;
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
            System.out.println(" 9 - Print Voted Blocks");
            System.out.println(" 10 - Start Accepting");

            String opAux = sysIn.nextLine();
            
            int option;
            
            if(opAux == null || opAux.equals("") || !isNumber(opAux)){
                option = -1;
            }else{
                option = Integer.parseInt(opAux);
            }
            
            
          

switch (option) {
    
    case -1:
        
        p2p.connect("10.0.0.11");
        p2p.connect("10.0.0.12");
        p2p.connect("10.0.0.13");
        
        break;
    
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
        
//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter("../../ballots/voting.txt", false);
//            fileWriter.write("");
//            fileWriter.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
//        }
//                
//        FileWriter fileWriter2 = null;
//        try {
//            fileWriter2 = new FileWriter("../../ballots/votingConfirm.txt", false);
//            fileWriter2.write("");
//            fileWriter2.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
//        }
                
                
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
        
        printVoted();
        
        
        break;
        
    case 10:
        
                
        startAccept();
        
        
        break;
        
        
    default:
        System.out.println("Wrong choice! try again.");
        break;
        
}

        } while (run);
        
    }
    
    private List<Block> ether = new ArrayList<>();
    private List<Block> voted = new ArrayList<>();
//    private String ADDRESS = "";
    
    private Ballot ballot;
    
    public void consensus(Block block){
        if(!this.etherHasBlock(block)){

            ether.add(block);
            p2p.publish(block);
        }
    }
    
    public float TOTAL_NODES = 4;
    
    public void startBallot(){
        
        System.out.println("STARTING NEW BALLOT");
        
        if(!ether.isEmpty()){
            if(!blockchain.haveBlock(ether.get(0).getHash())){
                
//                if( ADDRESS.equals("") || !ADDRESS.equals(ether.get(0).getHash()) ){
//                    ADDRESS = ether.get(0).getHash();
//                }
                
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("../../ballots/votes.txt", true);
                    fileWriter.write("node:"+ether.get(0)+"\n");
                    fileWriter.write("_vote_:"+this.nodeId+"\n");
                    fileWriter.close();
                    System.out.println("VOTED");
                } catch (IOException ex) {
                    Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                }

                ballot = new Ballot(ether.get(0).getHash(),TOTAL_NODES);
                localVoted.add(ballot.blockId);
                p2p.publish(ballot);
                
            }
        }
    }
    
    public List<String> localVoted = new ArrayList<>();
    public List<String> localVotedConfirmed = new ArrayList<>();
   
    public List<String> localAccepted = new ArrayList<>();
    public List<String> localAcceptedConfirmed = new ArrayList<>();
    
    
    public void receiveBallot(Ballot tempBallot){
        
        if(tempBallot.voted){
            // ACCEPTING
            
//            sleep(2000);

            if(!localAccepted.contains(tempBallot.blockId)){

                localAccepted.add(tempBallot.blockId);
                writeInBallot("_accept_");
                p2p.publish(tempBallot);

                System.out.println("ACCEPTED");
                

            }

            if(this.canvasAccepts() && !localAcceptedConfirmed.contains(tempBallot.blockId)){

                localAcceptedConfirmed.add(tempBallot.blockId);
                System.out.println("ADDING BLOCK TO BLOCKCHAIN");
                if(voted.get(0)!=null){
                    blockchain.addBlock(voted.get(0));
                    voted.remove(voted.get(0));
                }

            }

            if(!localAcceptedConfirmed.contains(tempBallot.blockId)){
                p2p.publish(tempBallot);
            }
            
        }else{
            // VOTING
            
            sleep(2000);

            if(!localVoted.contains(tempBallot.blockId)){
                localVoted.add(tempBallot.blockId);
                writeInBallot("_vote_");
                p2p.publish(tempBallot);
                System.out.println("VOTED");
                

            }

            if(this.canvasVotes() && !localVotedConfirmed.contains(tempBallot.blockId)){
                localVotedConfirmed.add(tempBallot.blockId);
                System.out.println("CONSENSUS REACHED");
                if(ether.get(0)!=null){
                    voted.add(ether.get(0));
                    ether.remove(ether.get(0));
                    startAccept();
//                    p2p.publish(tempBallot);
                }

            }

            if(!localVotedConfirmed.contains(tempBallot.blockId)){
                p2p.publish(tempBallot);
            }

        }
           
    }
    
     
    public void startAccept(){
        ballot = new Ballot(voted.get(0).getHash(),TOTAL_NODES);
        ballot.voted = true;
        receiveBallot(ballot); 
    }
    
    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeInBallot(String type){
        FileWriter fileWriter = null;
        try {
            if(!voted.isEmpty()){
                fileWriter = new FileWriter("../../ballots/votes.txt", true);
            }else{
                fileWriter = new FileWriter("../../ballots/votes.txt", true);
            }
            
            fileWriter.write(type+":"+this.nodeId+"\n");
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("WRITE BALLOT ERROR");
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean canvasVotes() {
        List<String> lines = new ArrayList<>();
        float counter = 0;
        try{
            File file = new File("../../ballots/votes.txt"); 
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
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, X);
        }
        return false;
    }
    
    public boolean canvasAccepts() {
        List<String> lines = new ArrayList<>();
        float counter = 0;
        try{
            File file = new File("../../ballots/votes.txt");
            Scanner sc = new Scanner(file); 

            while (sc.hasNextLine()){
                String auxTempStr = sc.nextLine();
                String [] tempStr = auxTempStr.split(":");
                if(tempStr[0].equals("_accept_")){
                    counter++;
                    lines.add(auxTempStr);
                }
                
            }
            
            if(counter-1 >= (TOTAL_NODES/3)*2){
                return true;
            }
            
        }catch(Exception X){
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, X);
        }
        return false;
    }
    
    public boolean alreadyVoted(){
        List<String> lines = new ArrayList<>();
        int counter = 0;
        try{
            File file = new File("../../ballots/votes.txt"); 
            Scanner sc = new Scanner(file); 

            while (sc.hasNextLine()){
                String [] tempStr = sc.nextLine().split(":");
                if(tempStr.equals(this.nodeId)){
                    return true;
                }
            }
            
        }catch(Exception X){
            System.out.println("Error: "+X.getMessage());
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, X);
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
                return true;
            }
        }
        return false;
    }
    
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
        return retString.toString();
    }
    
    public void printVoted(){
        for (int i = 0; i < voted.size(); i++) {
            System.out.println("Voted: "+voted.get(i).getData());
        }
    }
    
    
}