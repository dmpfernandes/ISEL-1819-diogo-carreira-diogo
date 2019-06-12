//package tp1.server_client_communication;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//import tp1.service_handler.Service;
////import java.util.Scanner;
//
//public class Server {
//	
//	private Thread t;
//	private Service s;
//	
//	private boolean running = true;
//	private int port = 1571;
//	static ServerSocket server;
//	Socket service;
//
//	public Server() {
//		
//	}
//	
//	public void run(){	
//		while(running) {
//			try { 
//				server= new ServerSocket(port); 
//				System.out.println(" espera...");
//				service = server.accept();
//				server.close();
//			}
//			catch (IOException e) {}
//			s = new Service(service);
//			t = new Thread(s);
//			t.start();
//		}
//		
//	}
//	//Recebo: <resposta numeroAluno="12346" indexPergunta="3" indexResposta="0"/>
//	String resultadoStr = "<resultado idxPergunta='";
//	String respStr = apanhar();
//	Element resp = readXML(respStr);
//	if(resp.hasChildNodes()) {
//		String numeroAluno = resp.getAttribute("numeroAluno");
//		String idxPergunta = resp.getAttribute("indexPergunta");
//		String idxResposta = resp.getAttribute("indexResposta");
//		Element pergunta = (Element)rootDBitems.getChildNodes().item(Integer.valueOf(idxPergunta));
//		Element resposta = (Element)pergunta.getElementsByTagName("resp").item(Integer.valueOf(idxResposta));
//		if(resposta.getAttribute("valid").equals("true")) {
//			resultadoStr += idxPergunta + "' sucesso='true' resp='" + idxResposta;
//		} else {
//			resultadoStr += idxPergunta + "' sucesso='false' resp='" + idxResposta;
//		}
//		atirar(resultadoStr);
//	}
//	
//	
//} 


