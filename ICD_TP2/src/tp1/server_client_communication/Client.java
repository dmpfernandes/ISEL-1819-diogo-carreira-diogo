package tp1.server_client_communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	Socket cliente;	
	String host = "localhost";
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	static final int numeroPorto= 1571; // o n�mero do porto tem de ser um n�mero maior que 1024 e que n�o esteja a ser usado
	
	public Client() {
		
		try {
			cliente= new Socket(host, numeroPorto);
			outStream = new ObjectOutputStream(cliente.getOutputStream()); 
			inStream = new ObjectInputStream(cliente.getInputStream()); 
			System.out.println("Ligacao ao servidor estabelecida.");
			}
		catch (IOException e) {
			System.out.println("Nao foi encontrado um servidor.");
			System.exit(0);
		}
	}
	
	public void enviarMsg(String out) {
		try {
			outStream.writeObject(out);
		} catch (IOException e) {}
	}
	
	public void limparBuffer() {
		try {
			outStream.reset();
		} catch (IOException e) {}
	}
	
	public String receberMsg() {
		String in = "";
		try { in = (String) inStream.readObject(); }
		catch (IOException e) {} catch (ClassNotFoundException e) {}
		return in;
	}
	
	public void fecharCanal() {
		try {
			outStream.close(); 
			inStream.close();
			cliente.close();
		}
		catch (IOException e) { 
			 e.printStackTrace(); 
		}
	}
} 