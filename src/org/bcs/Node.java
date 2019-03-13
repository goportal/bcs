package org.bcs;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;


/**
 *
 * @author Portal
 */
public class Node {

    Utils utils = new Utils();
    Chain blockchain = new Chain();
    Server cServer = new Server();
    Thread server = new Thread(cServer);
    Scanner sysIn = new Scanner(System.in);

    public void run() {

        boolean run = true;
        do {
            System.out.println("Choose the option:");
            System.out.println(" 0 - Add a new block");
            System.out.println(" 1 - Print the blockchain");
            System.out.println(" 2 - Validate chain");
            System.out.println(" 3 - Check for blockchain updates");
            System.out.println(" 4 - Tamper the chain");
            System.out.println(" 5 - Exit");
            System.out.println(" 6 - Start a server");
            System.out.println(" 7 - Start a client");
            
            
            int option = Integer.parseInt(sysIn.nextLine());

            switch (option) {
                case 0:
                    System.out.println("What content do you want to put in the block?");
                    String data = sysIn.nextLine();

                    int index = blockchain.getIndex();
                    String previousHash = blockchain.getCanonicalHash();
                    String hash = utils.SHA256("" + index + previousHash + data);

                    Block newBlock = new Block(index, previousHash, hash, data);

                    blockchain.addBlock(newBlock);
                    
                    sendNodesUpdate(newBlock);

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
                    System.out.println("Bueller?");
                    break;

                case 4:
                    
                    System.out.println("Which Block?");
                    int nBlock = Integer.parseInt(sysIn.nextLine());
                    
                    System.out.println("Insert the new content:");
                    String newContent = sysIn.nextLine();
                    
                    blockchain.tamper(nBlock, newContent);
                    
                    break;

                case 5:
                    stopServer();
                    
                    run = false;
                    break;
                    
                case 6:
                    startServer();
                    break;
                
                case 7:
                    System.out.println("Which IP?");
                    String ip = sysIn.nextLine();
                    //client(ip);
                    break;

                default:
                    System.out.println("Wrong choice! try again.");
                    break;
            }

        } while (run);

    }

//    TCP server
    public void startServer() {
        server.start();
    }
    
    public void stopServer(){
        System.exit(0);
    }
    
    public void sendNodesUpdate(Block tempBlock){
//        server
        cServer.sendNodesUpdate(tempBlock);
    }

}
