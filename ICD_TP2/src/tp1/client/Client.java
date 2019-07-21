package tp1.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	Socket cliente;	
	String tipoCliente;
	String host = "localhost";
	ObjectOutputStream outStream;
	static final int numeroPorto= 1571; // o n�mero do porto tem de ser um n�mero maior que 1024 e que n�o esteja a ser usado
	
	public Client() {
		
		try {
			cliente= new Socket(host, numeroPorto);
			outStream = new ObjectOutputStream(cliente.getOutputStream()); 
			System.out.println("Ligacao ao servidor estabelecida.");
			
			this.tipoCliente = "";
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
	
	
	
	public void fecharCanal() {
		try {
			outStream.close(); 

			cliente.close();
		}
		catch (IOException e) { 
			 e.printStackTrace(); 
		}
	}

	public Socket getCliente() {
		return cliente;
	}
	
	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
} 