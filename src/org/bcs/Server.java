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
//public class Server implements Runnable {
public class Server {
//    private volatile boolean exit = false;

    private List<Socket> nodes = new ArrayList<>();

    public Server() {
        new Thread(() -> {

            try {
                // Start ServerSocket listening port 7001
                ServerSocket server = new ServerSocket(7001);
                System.out.println("Server listening in 7001");
                while (!Thread.interrupted()) {
                    // o método accept() bloqueia a execução até que o servidor receba um pedido de conexão
                    Socket tempNode = server.accept();
                    nodes.add(tempNode);
                    System.out.println("New node added: " + tempNode.getInetAddress().getHostAddress());
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        }).start();
    }

//    @Override
//    public void run() {
//
//        try {
//            // Start ServerSocket listening port 7001
//            ServerSocket server = new ServerSocket(7001);
//            System.out.println("Server listening in 7001");
//            while (!Thread.interrupted()) {
//                // o método accept() bloqueia a execução até que o servidor receba um pedido de conexão
//                Socket tempNode = server.accept();
//                nodes.add(tempNode);
//                System.out.println("New node added: " + tempNode.getInetAddress().getHostAddress());
//            }
//        } catch (Exception e) {
//            System.out.println("Erro: " + e.getMessage());
//        }
//
//    }

    public void connect(String ip) {

        new Thread(() -> {
            try {
                Socket client = new Socket(ip, 7001);
                nodes.add(client);
                ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                while(true){
                    Block newBlock = (Block) input.readObject();
                    System.out.println("new block data:" + newBlock.getData());
                    //input.close();
                }
            } catch (Exception X) {
                System.out.println("ERROR: " + X.getMessage());
            }

        }).start();
//            input.close();
    }

    public void sendNodesUpdate(Block tempBlock) {
        try {
            for (int i = 0; i < nodes.size(); i++) {
                ObjectOutputStream out = new ObjectOutputStream(nodes.get(i).getOutputStream());
                out.flush();
                out.writeObject(tempBlock);
                out.reset();
//                out.close();
            }
        } catch (Exception X) {
            System.out.println("SEND NODES UPDATE ERROR: "+ X.getMessage());
        }
    }

    public void closeNodes() {
        for (int i = 0; i < nodes.size(); i++) {
            try {
                nodes.get(i).close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("CLOSE NODES ERROR");
            }
        }
    }

    public List<String> getConnectedNodes() {

        List<String> nodesIp = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            nodesIp.add(nodes.get(i).getRemoteSocketAddress().toString());
        }
        nodes.get(0).getInetAddress();

        return nodesIp;
    }

    //    TCP client
//    public void client(String ip) {
//        try {
//            
//        } catch (Exception e) {
//            System.out.println("Erro: " + e.getMessage());
//        }
//    }
//    public void myStop(){
//        exit = true;
//    }
}
