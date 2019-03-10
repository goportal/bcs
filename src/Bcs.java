
import java.util.Scanner;
import org.bcs.Node;
import org.bcs.Utils;

/**
 *
 * @author Portal
 */
public class Bcs {

    public static void main(String[] args) {
        
        Utils utils = new Utils();
        Scanner sc = new Scanner(System.in); 
        Node node = new Node();
        
//        System.out.println("- insert 0 to server \n- insert 1 to client");
//        String cont = sc.nextLine();
//        
//        if(cont.equals("0")){
//            System.out.println("server on");
//            node.server();
//        }else{
//            System.out.println("client on");
//            node.client(cont);
//        }

        System.out.println(" - Insert the text to encrypt");
        String cont = sc.nextLine();
        String cypher = utils.SHA256(cont);
        
        System.out.println("\n The cypher is: "+cypher+"\n");
        
        System.out.println("Ending test");
    }
    
}
