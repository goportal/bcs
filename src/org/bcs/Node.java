package org.bcs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author Portal
 */
public class Node {

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
    public void server() {
        System.out.println("here0");
        try {
            // Instancia o ServerSocket ouvindo a porta 7001
            System.out.println("here");
            ServerSocket servidor = new ServerSocket(7001);
            System.out.println("Servidor ouvindo a porta 7001");
            while (true) {
                // o método accept() bloqueia a execução até que o servidor receba um pedido de conexão
                Socket client = servidor.accept();
                System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(client.getOutputStream());
                saida.flush();
                saida.writeObject(new Date());
                saida.close();
                client.close();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

//    TCP client
    public void client(String ip) {
        System.out.println("here0");
        try {
            Socket cliente = new Socket(ip, 7001);
            System.out.println("here");
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            Date data_atual = (Date) entrada.readObject();
            System.out.println("Data recebida do servidor:" + data_atual.toString());
            //JOptionPane.showMessageDialog(null, "Data recebida do servidor:" + data_atual.toString());
            entrada.close();
            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
