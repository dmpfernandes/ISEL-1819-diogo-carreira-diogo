package tp1.server_client_communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import tp1.service_handler.Service;

public class ServidorUDP {

    public final static int DIM_BUFFER  = 1000;
    
    private Service service;
    Thread t;

    public static void main(String args[]) {

    } // end main

    public void run() {

        int port = 5025;

        DatagramSocket socketUDP = null;


        try { 
            // Cria socket - UDP no porto indicado (well-known port)
            // Este construtor pode gerar uma excep��o SocketException o que significa que ou
            // existe outro programa a utilizar o porto pretendido ou o socket est� a ser 
            // associado a um porto entre 1 e 1023 sem privil�gios de administrador 
            // (como, por exemplo, no sistema UNIX)
//            socketUDP = new DatagramSocket(port);

            // Cria um datagramaPacket para recep��o
        	
            byte inputBuffer[]  = new byte[DIM_BUFFER];
            DatagramPacket inputPacket = new DatagramPacket(inputBuffer, inputBuffer.length);

            for ( ; ; ) {
//            		try {
                        System.out.println("Servidor aguarda recepcao de mensagem no porto " + port);

                        // Recep��o de um datagrama
                        inputPacket.setLength(DIM_BUFFER);
//                        socketUDP.receive(inputPacket);
//
//                        String messageStr = new String(inputPacket.getData(), 0, inputPacket.getLength());
//                        
//                        System.out.println("Dados recebidos: " + messageStr);
//                        System.out.println("Numero de bytes recebidos: " + inputPacket.getLength());
//                        System.out.println("Endereco do cliente: " + inputPacket.getAddress()
//                                + " Porto: " + inputPacket.getPort());
//
//                        // Criar um datagrama para enviar a resposta
//                        messageStr = "@" + messageStr.toUpperCase();
//                        DatagramPacket outputPacket = new DatagramPacket(messageStr.getBytes(), messageStr.length(), 
//                                inputPacket.getAddress(), inputPacket.getPort());
                        socketUDP = new DatagramSocket(port);
                        service = new Service(socketUDP);
            			service.run();
            			
                        // Enviar datagrama de resposta 
                        //socketUDP.send(outputPacket);
//                    } 
//                    catch (IOException e) {
//                        System.err.println("Erro nas comunicacoes: " + e.getMessage());
//                    }
            } // end for
        } 
        catch (SocketException e) {
            System.err.println("Erro na criacao do socket: " + e.getMessage());
        }
    }
} // end ServidorUDP
