package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server  
{ 
	// initialize
	private static ArrayList <User> usersDB = new ArrayList<>();
	private static ArrayList <Thread> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(10);
	private static ServerSocket ss;
	
	// check user details method
	public static String ckUser ( String id, String password ) {
	String details = "No user with such "+id;
	
	for (User user: usersDB) {
		if (user.getId().equals(id))
				details = user.toString();
	}
	return details;
	}
	
	public static void main(String[] args) throws IOException  
    { 
		// initialize ServerSocket
		ss = new ServerSocket(9090); 
        System.out.println("[server] waiting for client connection...");  
        // running infinite loop for getting 
        // client request 
        
        // initialize users_DB
        			//new User(id,firstname,lastname, phone, password)
        usersDB.add(new User("111","rotem","nahum", "0526665845", "123"));
        usersDB.add(new User("222","ohad", "kubi",  "0526665248", "321"));
        usersDB.add(new User("333","ofer", "sil",   "0542225568", "123"));
        usersDB.add(new User("444","shlom","kuz",   "0535556254", "321"));
        usersDB.add(new User("555","guy",  "huhu",  "0526665845", "123"));
        
        while (true)  
        { 
            Socket s = null; 
            try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                System.out.println("\nA new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object for the client
                Thread t = new ClientHandler (s, dis, dos, clients); 
                clients.add(t);
                // Invoking the start() method 
                
                //t.start(); 
                
                pool.execute(t);
                
                System.out.println(  );
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 