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
import org.xml.sax.InputSource;

public class MessageReceiver implements Runnable {
	
	JTextArea board;
	ClientGUI cGUI;
	Client c;
	ObjectInputStream inStream;
	
	public MessageReceiver(ClientGUI cGUI, JTextArea board, Client c) {
		this.board=board;
		this.c =c;
		this.cGUI = cGUI;
	}

	@Override
	public void run() {
		try {
			inStream = new ObjectInputStream(c.getCliente().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		while(true) {
			 String msg = receberMsg();
			 Element xml = readXML(msg);
			 String choose = xml.getLocalName();
			 switch(choose) {
			 	case "login":
			 		if(xml.getAttribute("success").equals("true")&&xml.getAttribute("type").equals("prof")) {
			 			escreverMsg("Login Success");
			 			//cGUI.showMenu();
			 		}
			 		if(xml.getAttribute("success").equals("true")&&xml.getAttribute("type").equals("aluno")) {
			 			escreverMsg("Login Success");
			 			//cGUI.showInterfaceAluno();
			 		}else {
			 			escreverMsg("Login Unsuccessful");
			 			
			 		}
			 		break;
			 	case "adicionar":
			 		
			 		break;
			 	case "pergunta":
			 		break;
			 	case "resultado":
			 		break;
			 	case "perguntas":
			 		break;
			 	case "users":
			 		break;
			 		
			 }
				 
			 escreverMsg(msg);
		}
	}
	
	public void escreverMsg(String msg) {	
		board.setText(msg);
	}
	
	public String receberMsg() {
		String in = "";
		try { in = (String) inStream.readObject(); }
		catch (IOException e) {} catch (ClassNotFoundException e) {}
		return in;
	}
	
	public void limparBoard() {
		board.setText("");
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
	
}
