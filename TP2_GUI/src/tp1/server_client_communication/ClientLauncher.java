package tp1.server_client_communication;

import java.io.StringReader;
import java.util.Scanner;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class ClientLauncher {
	ClienteUDP c;
	Scanner sc;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientLauncher cl = new ClientLauncher();
		cl.run();
		
	}
	
	public ClientLauncher() {
		c = new ClienteUDP();
	}
	
	public void run() {
		while(true) {
			
			
			System.out.println("Que desejas");
			sc = new Scanner(System.in);
			String pedido = sc.nextLine();
			if(pedido.length() > 0) {
				c.atirar(pedido);
				String resposta = c.apanhar();
				
				System.out.println("recebido: " + resposta);
				if(resposta.contains("group")) {
					Element e = readXML(resposta);
					String group = e.getAttribute("group");
					c.joinGroup(group);
					System.out.println("joinGroup ("+group+") successfully");
					System.out.println("recebido em multi: " + c.apanharMulti());
					c.leaveGroup(group);
				}
				
			}
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
