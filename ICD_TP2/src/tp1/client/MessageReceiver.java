package tp1.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;

import javax.swing.JTextArea;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class MessageReceiver implements Runnable {
	
	
	ClientGUI cGUI;
	Client c;
	ObjectInputStream inStream;
	
	public MessageReceiver(ClientGUI cGUI, Client c) {
		this.c =c;
		this.cGUI = cGUI;
	}

	@Override
	public void run() {
		
		while(true) {
			 String msg = receberMsg();
			 if(msg != null && !msg.isEmpty()) {
				 Element xml = readXML(msg);
				 String choose = xml.getLocalName();
				 System.out.println("choose = "+choose);
				 switch(choose) {
				 	case "login":
				 		
				 		if(xml.getAttribute("success").equals("true")&&xml.getAttribute("type").equals("prof")) {
				 			cGUI.showMenu();
				 			escreverMsg("Login Success");
				 			
				 		}
				 		else if(xml.getAttribute("success").equals("true")&&xml.getAttribute("type").equals("aluno")) {
				 			cGUI.showInterfaceAluno();
				 			escreverMsg("Login Success");
				 			
				 		}else {
				 			escreverMsg("Login Unsuccessful");
				 			
				 		}
				 		break;
				 	case "adicionar":
				 		cGUI.showMenu();
				 		escreverMsg(xml.getTextContent());
				 		
				 		break;
				 	case "pergunta":
				 		if(xml.hasAttribute("status")) {
				 			cGUI.showMenu();
				 			escreverMsg("Pergunta enviada com status: "+xml.getAttribute("status"));
				 		}
				 		else {
				 			cGUI.showInterfaceAluno();
				 			escreverMsg(listarPergunta(xml));
				 			
				 		}
				 		
				 		break;
				 	case "resultado":
				 		cGUI.showInterfaceAluno();
				 		String sucesso = xml.getAttribute("sucesso");
				 		if(sucesso.equals("true")) {
				 			escreverMsg("Resposta Certa");
				 		}
				 		else { 
				 			escreverMsg("Resposta Errada");
				 		}
				 		break;
				 	case "perguntas":
				 		cGUI.showPerguntas();
				 		escreverMsg(listarPerguntas(xml));	
				 		break;
				 	case "users":
				 		cGUI.showEnviarPergunta();
				 		escreverMsg(listarUsers(xml));	
				 		break;
				 		
				 }
			 } 
		}
	}
	
	public void escreverMsg(String msg) {	
		JTextArea board = cGUI.getTextArea();
		board.setText(msg);
	}
	
	public String receberMsg() {
		String in = "";

		try { 
			inStream = new ObjectInputStream(c.getCliente().getInputStream());
			in = (String) inStream.readObject(); 
			inStream.reset();
			}
		catch (IOException e) {} catch (ClassNotFoundException e) {}
		return in;
	}
	
	public Element readXML(String xml) {
		
		XPathFactory xpathFactory = XPathFactory.newInstance(); 
		XPath xpath = xpathFactory.newXPath(); 
		InputSource source = new InputSource(new StringReader(xml)); 
		try {
			Document doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
			return doc.getDocumentElement();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public String listarPerguntas(Element xml) {
		NodeList perguntas = xml.getChildNodes();
		String msg = "";
		for (int i = 0; i < perguntas.getLength(); i++) {
			if(perguntas.item(i).hasAttributes()) {
				msg += listarPergunta((Element) perguntas.item(i));
			}

		}
		return msg;
	}
	public String listarPergunta(Element xml) {
			String duracao = xml.getAttribute("duracao");
			String id = xml.getAttribute("id").substring(2);
			String tema = xml.getAttribute("tema");
			String titulo = xml.getElementsByTagName("texto").item(0).getTextContent();
			NodeList respPossiveis = xml.getElementsByTagName("resp");
			String resp = "";
			int count = 0;
			for (int j = 0; j < respPossiveis.getLength(); j++) {
				
				resp += "	"+count + ". " + respPossiveis.item(j).getTextContent() + "\n";
				count++;
			}
			return id+"- "+titulo+"\n Tema: "+tema+"\n"+resp + "\n\n";
			
	}
	
	
	private String listarUsers(Element xml) {
		NodeList users = xml.getChildNodes();
		String msg = "";
		for (int i = 0; i < users.getLength(); i++) {
			if(users.item(i).hasAttributes()) {
				String tipo = ((Element) users.item(i)).getAttribute("tipo");
				String uid = ((Element) users.item(i)).getAttribute("uid");
				String nome = ((Element) users.item(i)).getElementsByTagName("nome").item(0).getTextContent();
				String nAluno = ((Element) users.item(i)).getElementsByTagName("numero").item(0).getTextContent();
				msg += "Utilizador: "+uid+"\n Nome: "+nome+"\n Nº Aluno: ";
				if(nome != "admin") msg+= nAluno;
				msg += "\n\n";
			}
		}
		
		
		return msg;
	}
	
}
