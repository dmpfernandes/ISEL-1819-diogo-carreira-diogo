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
				 		cGUI.showMenu();
			 			escreverMsg(xml.getTextContent());
				 		
				 		break;
				 	case "resultado":
				 		cGUI.showInterfaceAluno();
				 		String sucesso = xml.getAttribute("success");
				 		if(Boolean.valueOf(sucesso)) {
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
				String duracao = ((Element) perguntas.item(i)).getAttribute("duracao");
				String id = ((Element) perguntas.item(i)).getAttribute("id").substring(2);
				String tema = ((Element) perguntas.item(i)).getAttribute("tema");
				String titulo = ((Element) perguntas.item(i)).getElementsByTagName("texto").item(0).getTextContent();
				NodeList respPossiveis = ((Element) perguntas.item(i)).getElementsByTagName("resp");
				String resp = "";
				int count = 0;
				for (int j = 0; j < respPossiveis.getLength(); j++) {
					count++;
					resp += "	"+count + ". " + respPossiveis.item(j).getTextContent() + "\n";
				}
				msg += "Pergunta "+id+": "+titulo+"\n Tema: "+tema+"\n"+resp + "\n\n";
				
			}
		}
		
		
		return msg;
	}
	
}
