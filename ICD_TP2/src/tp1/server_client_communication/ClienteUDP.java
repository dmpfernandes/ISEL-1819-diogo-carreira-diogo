package tp1.server_client_communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClienteUDP {
	public final static int DIM_BUFFER  = 1000;
	public DatagramPacket inputPacket;
	public DatagramSocket socket;
	public InetAddress hostAddress;
	String host = "localhost";
    int    port = 5025;
    
    
    public ClienteUDP() {

        try { 
            // Obt�m endere�o do servidor 
            hostAddress = InetAddress.getByName(host);

            // Cria socket - UDP com um porto atribu�do dinamicamente pelo sistema (anonymous port)
            socket = new DatagramSocket();
            byte inputBuffer[]  = new byte[DIM_BUFFER];
    		inputPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
            
        } // end try
        catch (UnknownHostException e) {
            System.err.println("Servidor " + host + " nao foi encontrado");
        }
        catch (SocketException e) {
            System.err.println("Erro na criacao do socket: " + e.getMessage());
        }
       
    } // end main
    
	public void atirar(String out) {
		try {
			DatagramPacket outputPacket = new DatagramPacket(out.getBytes(), out.length(), 
					hostAddress, port);
			socket.send(outputPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String apanhar() {
		try {
			socket.receive(inputPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return new String(inputPacket.getData(), 0, inputPacket.getLength()); // Ler tudo incluindo headers
		return new String(inputPacket.getData(), inputPacket.getOffset(), inputPacket.getLength());
	}
	
	public void close() {
		if (socket != null) socket.close();
	}
    
} // end ClienteUDP
