package client;

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
// Client class 
public class Client  
{ 
    public static void main(String[] args) { 
        
    	try { 
    		//scanner - for input from the user
            Scanner input = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 9090); 
      
            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
            // the following loop performs the exchange of 
            // information between client and client handler 
            while (true) {
            	//Print instructions
            	System.out.println(dis.readUTF());
                String id = input.nextLine();           	                  
                dos.writeUTF(id);
                // If user type Bye => client Exit and disconnect
                // If client sends Bye, close this connection  
                // and then break from the while loop 
                if(id.equalsIgnoreCase("bye")) 
                { 
                    System.out.println("Closing this connection : " + s); 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }
                //continu got from the user
                System.out.println("Type Password: ");
                String password = input.nextLine();
                dos.writeUTF(password); 
                
                // printing User details
                String details = dis.readUTF(); 
                System.out.println(details); 
            } 
              
            // closing resources 
            input.close(); 
            dis.close(); 
            dos.close(); 
            
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 