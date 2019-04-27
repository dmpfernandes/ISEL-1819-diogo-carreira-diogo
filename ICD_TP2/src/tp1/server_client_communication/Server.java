package tp1.server_client_communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tp1.service_handler.Service;
//import java.util.Scanner;

public class Server {
	
	private Thread t;
	private Service s;
	
	private boolean running = true;
	private int port = 1571;
	static ServerSocket server;
	static Socket service;

	public Server() {
		
	}
	
	public void run(){	
		while(running) {
			try { 
				server= new ServerSocket(port); 
				System.out.println(" espera...");
				service = server.accept();
				server.close();
			}
			catch (IOException e) {}
			s = new Service(service);
			t = new Thread(s);
			t.start();
		}
		
	}
} 


