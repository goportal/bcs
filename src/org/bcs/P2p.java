package org.bcs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author portal
 */
public class P2p {

    private ServerSocket server;
    private List<String> nodesIp = new ArrayList<>();
    private List<ObjectOutputStream> outputs = new ArrayList<>();
//    private List<ObjectInputStream> inputs = new ArrayList<>();

    public P2p() {
        new Thread(() -> {

            try {
                // Start ServerSocket listening port 7001
                server = new ServerSocket(7001);
                System.out.println("P2P server started at port 7001");

                while (true) {

                    Socket tempNode = server.accept();

                    String tempNodeIp = tempNode.getInetAddress().getHostAddress();

                    if (!nodesIp.contains(tempNodeIp)) {
                        
                        nodesIp.add(tempNodeIp);
                        connect(tempNodeIp);
                        
                        ObjectInputStream input = new ObjectInputStream(tempNode.getInputStream());

                        new Thread(() -> {
                            try {
                                while (true) {
                                    Block newBlock = (Block) input.readObject();
                                    System.out.println("new block data:" + newBlock.getData());
                                }
                            } catch (Exception X) {
                                System.err.println("ERROR: " + X.getMessage());
                            }
                        }).start();

                        System.out.println("New node added: " + tempNode.getInetAddress().getHostAddress());
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        }).start();
    }

    public void connect(String ip) {

//        if (!nodesIp.contains(ip)) {
            
            try {
                Socket client = new Socket(ip, 7001);
//            nodes.add(client);

//            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
//
//            new Thread(() -> {
//                try {
//                    while (true) {
//                        Block newBlock = (Block) input.readObject();
//                        System.out.println("new block data:" + newBlock.getData());
//                    }
//                } catch (Exception X) {
//                    System.err.println("ERROR: " + X.getMessage());
//                }
//            }).start();
                ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
                output.flush();

                outputs.add(output);

                System.out.println("Connected to the server in: " + ip);

//                while(true){
//                    Block newBlock = (Block) input.readObject();
//                    System.out.println("new block data:" + newBlock.getData());
//                    //input.close();
//                }
            } catch (Exception X) {
                System.out.println("ERROR: " + X.getMessage());
            }
//        }
//        }).start();
    }

    public void publish(Block block) {
        try {
//            System.out.println("hereeeee");
            for (int i = 0; i < outputs.size(); i++) {
//                System.out.println("hereeeee in :"+i);
                outputs.get(i).writeObject(block);
            }
        } catch (Exception X) {
            System.err.println("ERROR: " + X.getMessage());
        }
    }

//    
}
