package tp1.service_handler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class Service implements Runnable{
	Socket s;
	ObjectOutputStream os;
	ObjectInputStream is;
	Document document;
	Element root;
	Element rootDBitems;
	Element rootDBusers;
	String userTipo = null;
	String name = null;
	boolean running = true;
	Integer ID_SERVICE = null;

	
	public Service(Socket socket) {
		this.s = socket;
	}
	
	private void setIdService() {
		ID_SERVICE = s.hashCode();
	}
	
	

	@Override
	public void run() {
		setIdService();
		try {
			os = new ObjectOutputStream(s.getOutputStream()); 
			is = new ObjectInputStream(s.getInputStream()); 
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while(running) {
			try {				

				String inputLine = apanhar();
				if(inputLine != null && !inputLine.isEmpty()) {
					root = readXML(inputLine);
					String choose = root.getLocalName();
					rootDBitems = readXMLfromFile("items.xml");
					rootDBusers = readXMLfromFile("users.xml");
					System.out.println(rootDBusers.getChildNodes().item(0).getNodeName());
					switch(choose){
					case "login": // LOGIN
						rootDBusers = readXMLfromFile("users.xml");
						NodeList users = rootDBusers.getChildNodes();
						boolean atirou=false;
						if (root.getNodeType()==Node.ELEMENT_NODE && root.hasAttributes()) {
							String numero = root.getAttribute("numero");
							String tipo = root.getAttribute("tipo");
							System.out.println("input numero - "+numero);
							System.out.println("input tipo - "+tipo);
							for (int i = 0; i < users.getLength(); i++) {
								System.out.println(users.getLength());
								String tipoDBuser = users.item(i).getAttributes().getNamedItem("tipo").getTextContent();
								String numeroDBuser = users.item(i).getChildNodes().item(1).getLocalName();
								System.out.println("DB numero - "+tipoDBuser);
								System.out.println("DB tipo - "+numeroDBuser);
//								if(users.item(i).hasAttributes()) {
//									NamedNodeMap user_att = users.item(i).getAttributes();
//									
//									
//									String dbuserTipo = user_att.getNamedItem("tipo").getTextContent();
//				                    
//				                    this.name = root.getChildNodes().item(0).getTextContent();
//				                    String namedb = users.item(i).getChildNodes().item(1).getTextContent();
//				                    String passdb = users.item(i).getChildNodes().item(3).getTextContent();
//				                    String namelogin = root.getChildNodes().item(0).getTextContent();
//				                    String pass = root.getChildNodes().item(1).getTextContent();
//									if(namedb.equals(name) && passdb.equals(pass) && dbuserTipo.equals("f")) {
//										this.userTipo = dbuserTipo;
//										atirar("Bem Vindo Funcionario "+users.item(i).getChildNodes().item(1).getTextContent());
//										atirou = true;
//									}
//									if(namedb.equals(name) && passdb.equals(pass) && dbuserTipo.equals("c")) {
//										this.userTipo = dbuserTipo;
//										atirar("Bem Vindo Cliente "+users.item(i).getChildNodes().item(1).getTextContent());
//										atirou = true;
//									}
//									if(namedb.equals(name) && passdb.equals(pass) && dbuserTipo.equals("a")) {
//										this.userTipo = dbuserTipo;
//										atirar("Bem Vindo Administrador "+users.item(i).getChildNodes().item(1).getTextContent());
//										atirou = true;
//									}
//								}
							}
						} 
						if(!atirou) {
							atirar("User nao registado");
						}
						break;
					case "adicionarPergunta": // GET 	
						
						break;
						
						
					case "selecionarPergunta": // POST
						
						break;
					case "kill": // kill
						System.out.println("casekill");
						atirar("Conneccao Terminada");
						fecharCanal();
						running = false;
						break;
	
					}
					System.out.println();
				}
			} finally {
				System.out.println("sai");
			}
		} // end while
	}
	



	void atirar(String out) {
		try {
			os.writeObject(out);
		} catch (IOException e) {}
	}
	
	void limpar() {
		try {
			os.reset();
		} catch (IOException e) {}
	}
	
	String apanhar() {
		String in = "";
		try { in = (String) is.readObject(); }
		catch (IOException e) {} catch (ClassNotFoundException e) {}
		return in;
	}
		
	boolean verificar(String msg, String robot) {
		System.out.println("Client may connect: " + (!msg.contains(robot)));
		if (!msg.contains(robot)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	void fecharCanal() {
		try {
			os.close(); 
			is.close();
			s.close();
		}
		catch (IOException e) { 
			 e.printStackTrace(); 
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
			//e.printStackTrace();
			atirar("Saiu");
		} 
		return null;
		
	}
	
	public Element readXMLfromFile(String path) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(path);
			return document.getDocumentElement();

		} catch (Exception e) {
			//e.printStackTrace(System.out);
			System.out.print("Erro ao analisar o documento XML.");
		}
		return null;
	}
	
//	public String getAllItems() {
//		NodeList items = rootDB.getElementsByTagName("item");
//		String aux = "";
//		for(int x=0,size= items.getLength(); x<size; x++) {
//			try {
//				aux += nodeToString(items.item(x)) + "\n";
//			} catch (TransformerException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return aux;
//	}
	
	
	
	public static void outputXML(Document document, String outFilename){
		try {
			
			// Use a Transformer for output
			TransformerFactory tFactory =
					TransformerFactory.newInstance();
			Transformer transformer = 
					tFactory.newTransformer();

			DOMSource source = new DOMSource(document);

			OutputStream out = new FileOutputStream(outFilename);
			StreamResult result = new StreamResult(out/*System.out*/);
			transformer.transform(source, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
//	
//	private static String nodeToString(Node node)
//			throws TransformerException
//			{
//			    StringWriter buf = new StringWriter();
//			    Transformer xform = TransformerFactory.newInstance().newTransformer();
//			    xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//			    xform.transform(new DOMSource(node), new StreamResult(buf));
//			    return(buf.toString());
//			}
//	

	
}
