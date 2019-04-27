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
					System.out.println(root.getLocalName());
					String choose = root.getLocalName();
					rootDBitems = readXMLfromFile("perguntas.xml");
					rootDBusers = readXMLfromFile("users.xml");
					switch(choose){
					case "login": // LOGIN
						rootDBusers = readXMLfromFile("users.xml");
						NodeList users = rootDBusers.getChildNodes();
						boolean atirou=false;
						if (root.getNodeType()==Node.ELEMENT_NODE && root.hasAttributes()) {
							String numero = root.getAttribute("numero");
							String tipo = root.getAttribute("tipo");
							for (int i = 1; i < users.getLength(); i++) {
								if(users.item(i).hasAttributes()) {
									String tipoDBuser = users.item(i).getAttributes().getNamedItem("tipo").getTextContent();
									Element e = (Element) users.item(i);
									String numeroDBuser = e.getElementsByTagName("numero").item(0).getTextContent();
									if(tipoDBuser.equals(tipo) && numeroDBuser.equals(numero)) {
										this.userTipo = tipo;
										atirar("Autenticação Validada");
										atirou = true;
										break;
									}
								}
							}
						} 
						if(!atirou) {
							atirar("User nao registado");
						}
						break;
					case "adicionarPergunta": // GET 	
						System.out.println(this.userTipo);
						if(this.userTipo != "prof" && this.userTipo != "admin") {
							atirar("Não tem autorização para adicionar perguntas.");
						}else {
							rootDBitems = readXMLfromFile("perguntas.xml");
							NodeList perguntas = rootDBitems.getChildNodes();
							if (root.getNodeType()==Node.ELEMENT_NODE && root.hasChildNodes()) {
								NodeList inputPerguntas = root.getChildNodes();
								for (int i = 0; i < inputPerguntas.getLength(); i++) {
									System.out.println(inputPerguntas.item(i).getLocalName());
									if(inputPerguntas.item(i).hasAttributes()) {
										Element e = (Element) inputPerguntas.item(i);
										e.setAttribute("id","_0"+(getlastPerguntaID()+1));
										rootDBitems.appendChild(e);
										outputXML(document,"perguntas.xml");
									}
								}
								atirar("Pergunta/s adicionada/s com sucesso.");
								
							}
						}
						
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
	
	public int getlastPerguntaID() {
		rootDBitems = readXMLfromFile("perguntas.xml");
		NodeList items = rootDBitems.getChildNodes();
		int counter = 0;
		for (int i = 0; i < items.getLength(); i++) {
			if (items.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) items.item(i);
				if(items.item(i).hasAttributes() && e.hasAttribute("id")) {
					counter++;
				}
			}
		}
		
	
		return counter;
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
