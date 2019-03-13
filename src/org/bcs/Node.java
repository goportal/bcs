package org.bcs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Portal
 */
public class Node {

    Utils utils = new Utils();
    Chain blockchain = new Chain();
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
                    run = false;
                    break;

                default:
                    System.out.println("Wrong choice! try again.");
                    break;
            }

        } while (run);

    }

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
