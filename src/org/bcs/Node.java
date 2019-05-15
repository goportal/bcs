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

    private List<Block> underVote = new ArrayList<>();
    private List<Ballot> ballots = new ArrayList<>();
    
    
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
            System.out.println(" 7 - KeyPair test");
            System.out.println(" 8 - Connect to slice");
            

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
                   
                    this.underVote.add(newBlock);
                    
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

                    KeyPair keys = this.utils.generateKeyPair();
                    
                    PublicKey pubKey = keys.getPublic();
                    PrivateKey privKey = keys.getPrivate();
                    
                    String content = "This is a text";
                    
                    System.out.println("plain text is: "+ content);
                    
                    byte[] cyText = utils.criptografa(content, pubKey);
                    
                    System.out.println("The encripted text is: "+cyText.toString());
                    
                    String decripted = utils.decriptografa(cyText, privKey);
                    
                    System.out.println("The decripted text is: "+decripted);
                    
                    
                    break;

                case 8:
                    
//                    connectToSlice(sysIn.nextLine());
                    
                    break;
                    
                default:
                    System.out.println("Wrong choice! try again.");
                    break;
                   
            }

        } while (run);

    }

    public void connectToSlice(String sliceName){
//        this.qSlices.add(sliceName);
    }
    
    public void consensus(Object message){
        Block block;
        Ballot ballot;
        
        if(message instanceof Block){
            block = (Block) message;
            
            if(!blockchain.haveBlock(block.getHash())){
                if(!underVote.contains(block)){
                    underVote.add(block);
                    Ballot vote = new Ballot(this.nodeId, block.getHash(),"_vote_");
                    
                    ballots.add(vote);
                    
                    p2p.publish(block);
                    p2p.publish(vote);
                    
                }
                
            }else{
                if(ballots.size()>=3){
                    this.blockchain.addBlock(block);
                }
            }
            
        }
//        else{
//            ballot = (Ballot) message;
//            if(!alreadyVoted(ballot)){
//                this.ballots.add(ballot);
//            }
//            if(!blockchain.haveBlock(ballot.blockId)){
//                
//            }
//            // verifica porcentagem do consenso
//        }
    }
    
    
    
    public boolean alreadyVoted(Ballot vote){
        for(int i=0;i<this.ballots.size();i++){
            Ballot tempBallot = this.ballots.get(i); 
            if(tempBallot.blockId.equals(vote.blockId) && tempBallot.nodeId.equals(vote.nodeId) && tempBallot.vote.equals("_vote_")){
                return true;
            }
        }
        return false;
    }
    
    
//    blockchain.addBlock(block);
    
//    TCP server
//    public void startServer() {
//        server.start();
//    }

}
