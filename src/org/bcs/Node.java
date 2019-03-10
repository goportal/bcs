package org.bcs;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Portal
 */
public class Node {

    List<Block> chain = new ArrayList<>();

//    private Socket getSocket(){
//        Socket client = new Socket();
//        try{
//            ServerSocket server = new ServerSocket(7001);
//            client = server.accept(); 
//        }catch(Exception X){
//            System.out.println("Socket Error");
//        }
//        return client;
//    }
//    TCP server
//    private void getSocket() {
//        try {
//            // Instancia o ServerSocket ouvindo a porta 12345
//            ServerSocket servidor = new ServerSocket(7001);
//            System.out.println("Servidor ouvindo a porta 7001");
//            while (true) {
//                // o método accept() bloqueia a execução até que o servidor receba um pedido de conexão
//                Socket cliente = servidor.accept();
//                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
//                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
//                saida.flush();
//                saida.writeObject(new Date());
//                saida.close();
//                cliente.close();
//            }
//        } catch (Exception e) {
//            System.out.println("Erro: " + e.getMessage());
//        }
//    }
//    TCP client
    
//    public class ClienteTCPBasico {
//
//        public static void main(String[] args) {
//            try {
//                Socket cliente = new Socket("paulo", 12345);
//                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
//                Date data_atual = (Date) entrada.readObject();
//                JOptionPane.showMessageDialog(null, "Data recebida do servidor:" + data_atual.toString());
//                entrada.close();
//                System.out.println("Conexão encerrada");
//            } catch (Exception e) {
//                System.out.println("Erro: " + e.getMessage());
//            }
//        }
//    }

    private void addBlock(Block block) {
        chain.add(block);
    }

}
