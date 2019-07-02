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
	Client c;
	MessageReceiver mr;
	Scanner sc;
	
	private Thread t;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientLauncher cl = new ClientLauncher();
		cl.run();
		
	}
	
	public ClientLauncher() {
		c = new Client();
		mr = new MessageReceiver(c);
		mr.run();
	}
	
	public void run() {
		while(true) {
			String msg = null;
			if(mr.getMsg() != null || mr.getMsg().isEmpty()) {
				msg = mr.getMsg();
				System.out.println("recebido: " +msg);
			}
			
			System.out.println("Que desejas");
			sc = new Scanner(System.in);
			String pedido = sc.nextLine();
			if(pedido.length() > 0) {
				c.enviarMsg(pedido);

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
