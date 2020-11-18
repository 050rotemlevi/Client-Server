package server;

import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler extends Thread  
{ 
	//initialise
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    
    private ArrayList <Thread> clients;
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, ArrayList <Thread> clients)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos;
        this.clients=clients;
    } 
  
    @Override
    public void run() { 
        while (true)  
        { 
            try { 
                  // Ask user what he wants 
                dos.writeUTF("\nType ID for details "+ 
                            "Or type Bye to Exit."); 
                  
                // receive the ID from client 
                String id = dis.readUTF();
                // if ID = BYE => Close, disconnect & remove from clients list
                if(id.equalsIgnoreCase("Bye")) 
                {  
                    System.out.println("\nClient " + this.s + " sends Bye"); 
                    System.out.println("Closing the connection.");
                    this.s.close(); 
                    System.out.println("Client connection closed");
                    clients.remove(this);
                    
                    //check when the server no have connections
                    if (clients.size() == 0)
                    	System.out.println("** No clients Connections :) **");
                    
                    break; 
                }
                String password = dis.readUTF();
                
                //check & return user details
                String details = Server.ckUser (id, password);
                dos.writeUTF(details);
                                                               
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 
