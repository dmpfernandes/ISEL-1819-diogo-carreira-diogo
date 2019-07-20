package tp1.service_handler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.standard.DateTimeAtCompleted;
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
    public final static int DIM_BUFFER  = 1000000;

    Socket s;
	ObjectOutputStream os;
	ObjectInputStream is;
	
	Element root;
	Element rootDBitems;
	Element rootDBusers;
	String userTipo = null;
	String name = null;
	boolean running = true;
	Integer ID_SERVICE = null;
	private static Map<String, Node> selecoes = new HashMap<String, Node>();
	
	private static Map<String,Map<InetAddress, Integer>> login = new HashMap<String,Map<InetAddress, Integer>>();

	
	
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
			
			is = new ObjectInputStream(s.getInputStream()); 
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while(running) {
			try {				

				String inputLine = apanhar();
				System.out.println(inputLine);
				if(inputLine != null && !inputLine.isEmpty()) {
					root = readXML(inputLine);
					System.out.println(root.getLocalName());
					String choose = root.getLocalName();
					
					switch(choose){
					case "login": // LOGIN
						rootDBusers = readXMLfromFile("users.xml");
						
						boolean atirou=false;
						if (root.getNodeType()==Node.ELEMENT_NODE && root.hasAttributes()) {
							NodeList users = rootDBusers.getChildNodes();
							String numero = root.getAttribute("numero");
							String pass = root.getAttribute("pass");
							for (int i = 1; i < users.getLength(); i++) {
								if(users.item(i).hasAttributes()) {
									String tipoDBuser = users.item(i).getAttributes().getNamedItem("tipo").getTextContent();
									Element e = (Element) users.item(i);
									String numeroDBuser = e.getElementsByTagName("numero").item(0).getTextContent();
									String passDBuser = e.getElementsByTagName("pass").item(0).getTextContent();
									if(passDBuser.equals(pass) && numeroDBuser.equals(numero)) {
										this.userTipo = tipoDBuser;
										Map<InetAddress, Integer> address = new HashMap<InetAddress, Integer>();
										address.put(s.getInetAddress(), s.getPort());
										System.out.println("address --> "+numeroDBuser+"   "+s.getInetAddress()+":"+s.getPort());
										login.put(numeroDBuser, address);
										if (tipoDBuser.equals("prof")) {
											atirar("<login success='true' type='prof'/>");
										}
										if (tipoDBuser.equals("aluno")) {
											atirar("<login success='true' type='aluno'/>");
										}
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
					case "adicionarPergunta": 
						rootDBusers = readXMLfromFile("users.xml");
						NodeList users = rootDBusers.getChildNodes();
						
						
						for (Entry<String, Map<InetAddress, Integer>> entry : login.entrySet()) {
							Map<InetAddress, Integer> address = new HashMap<InetAddress, Integer>();
							address.put(s.getInetAddress(), s.getPort());
				            if (entry.getValue().equals(address)) {
				                String numero = entry.getKey();
				                for (int i = 1; i < users.getLength(); i++) {
									if(users.item(i).hasAttributes()) {
										String tipoDBuser = users.item(i).getAttributes().getNamedItem("tipo").getTextContent();
										Element e = (Element) users.item(i);
										String numeroDBuser = e.getElementsByTagName("numero").item(0).getTextContent();
										if(numeroDBuser.equals(numero)) {
											if(!tipoDBuser.equals("prof") && !tipoDBuser.equals("admin")) {
												atirar("<adicionar>Não tem autorização para adicionar perguntas.</adicionar>");
											}else {
												rootDBitems = readXMLfromFile("perguntas.xml");
												if (root.getNodeType()==Node.ELEMENT_NODE && root.hasChildNodes()) {
													NodeList inputPerguntas = root.getChildNodes();
													for (int p = 0; p < inputPerguntas.getLength(); p++) {
														if(inputPerguntas.item(p).hasAttributes()) {
															Element ep = (Element) inputPerguntas.item(p);
															ep.setAttribute("id","_0"+(getlastPerguntaID()+1));
															Node imported = rootDBitems.getOwnerDocument().importNode(ep, true);
															rootDBitems.appendChild(imported);
															outputXML(rootDBitems.getOwnerDocument(),"perguntas.xml");
														}
													}
													atirar("<adicionar>Pergunta(s) adicionada(s) com sucesso.</adicionar>");
													
												}
											}
										}
									}
								}
				            }
				        }
						
						
						
						
						break;
					case "selecionarPergunta": 
						rootDBitems = readXMLfromFile("perguntas.xml");
						rootDBusers = readXMLfromFile("users.xml");
						for (Entry<String, Map<InetAddress, Integer>> entry : login.entrySet()) {
							Map<InetAddress, Integer> address = new HashMap<InetAddress, Integer>();
							address.put(s.getInetAddress(), s.getPort());
				            if (entry.getValue().equals(address)) {
								if (root.getNodeType()==Node.ELEMENT_NODE && root.hasAttributes()) {
									String idPergunta = "_0"+root.getAttribute("index");
									System.out.println("idPergunta: "+idPergunta);
									boolean todos = (root.getAttribute("todos").equals("true"))?true:false;
									
									NodeList perguntas = rootDBitems.getChildNodes();
									NodeList alunosDB = rootDBusers.getChildNodes();
									String perguntaSelecionada="";							
									
									for (int i = 0; i < perguntas.getLength(); i++) {
										if(perguntas.item(i).hasAttributes()) {
											Element e = (Element) perguntas.item(i);
											String id = e.getAttribute("id");
											if(id.equals(idPergunta)) {
												//aqui encontramos o element que contem a pergunta a fazer
												perguntaSelecionada = nodeToString(e.cloneNode(true));
											 
											}
										}
									}
									System.out.println(perguntaSelecionada);
									if(perguntaSelecionada.isEmpty()) {
							        	atirar("<pergunta index="+idPergunta+" status='failure'/>");
							        }
									
									if(!todos && root.hasChildNodes()) {
										NodeList alunosSelecionados = root.getChildNodes();
										for (int j = 0; j < alunosSelecionados.getLength(); j++) {
											for (int i = 0; i < alunosDB.getLength(); i++) {
												if(alunosSelecionados.item(j).hasAttributes()) {
													Element a = (Element) alunosSelecionados.item(j);
													String numeroAluno = a.getAttribute("numero");
													if(alunosDB.item(i).hasChildNodes()) {
														Element e = (Element) alunosDB.item(i);
														String numero = e.getElementsByTagName("numero").item(0).getTextContent();
														if(numero.equals(numeroAluno) || todos) {
													        if(login.containsKey(numero)) {
													        	Map<InetAddress, Integer> add = login.get(numero);  
													        	for ( Map.Entry<InetAddress, Integer> entry1 : add.entrySet()) {
													        		InetAddress path = entry1.getKey();
													        		Integer port = entry1.getValue();
													        		try {
																		Socket sa = new Socket(path,port);
																		atirar(perguntaSelecionada,sa);
																	} catch (IOException e1) {
																		// TODO Auto-generated catch block
																		e1.printStackTrace();
																	}
													        	}
													        }
														}
													}
												}	
											}
										}
									}
									if(todos) {
										for (int i = 0; i < alunosDB.getLength(); i++) {
											if(alunosDB.item(i).hasChildNodes()) {
												Element e = (Element) alunosDB.item(i);
												String numero = e.getElementsByTagName("numero").item(0).getTextContent();
												if(login.containsKey(numero)) {
													Map<InetAddress, Integer> add = login.get(numero);  
										        	for ( Map.Entry<InetAddress, Integer> entry1 : add.entrySet()) {
										        		InetAddress path = entry1.getKey();
										        		Integer port = entry1.getValue();
										        		if(!port.equals(s.getPort())) {
										        			try {
																Socket sa = new Socket(path,port);
																atirar(perguntaSelecionada,sa);
															} catch (IOException e1) {
																// TODO Auto-generated catch block
																e1.printStackTrace();
															}
										        		}
										        	}
												}
											}
										}
									}
									atirar("<pergunta index="+idPergunta+" status='success'/>");
								}
				            }
						}
						
						break;
					case "resposta": 
						//Recebo: <resposta numeroAluno="12346" indexPergunta="3" indexResposta="0"/>
						String resultadoStr = "<resultado idxPergunta='";
						
						
						if(root.hasAttributes()) {
							NodeList perguntas = rootDBitems.getChildNodes();
							String numeroAluno = root.getAttribute("numeroAluno");
							String idPergunta = "_0"+root.getAttribute("indexPergunta");
							String idxResposta = root.getAttribute("indexResposta");
							if(idxResposta.equals("")) {
								
							}
							Element perguntaSelecionada = null;
							for (int i = 0; i < perguntas.getLength(); i++) {
								if(perguntas.item(i).hasAttributes()) {
									Element e = (Element) perguntas.item(i);
									String id = e.getAttribute("id");
									if(id.equals(idPergunta)) {
										//aqui encontramos o element que contem a pergunta a fazer
										perguntaSelecionada = e;
										
									 
									}
								}
							}
							NodeList resps = perguntaSelecionada.getElementsByTagName("resp");
							for (int i = 0; i < resps.getLength(); i++) {
								Element e = (Element) resps.item(i);
								if(e.hasAttribute("resid")) {
									if(e.getAttribute("resid").equals(idxResposta)) {
										String valid = e.getAttribute("valid");
										String resultado = "<resultado idxPergunta='"+idPergunta + "' sucesso='"+valid+"' resp='" + idxResposta+" ></resultado>";
										String log = "<log data='" + new java.util.Date() +"' idxPergunta='"+idPergunta + "' sucesso='"+valid+"' resp='" + idxResposta+"' />";
										Node logXml = readXML(log);
										Element logs = (Element)readXMLfromFile("logs.xml");
										logs.appendChild(logs.getOwnerDocument().importNode(logXml, true));
										System.out.println("logs:" + logs);
										System.out.println("log:" + log);
										outputXML(logs.getOwnerDocument(), "logs.xml");
										
//										rootDBitems = readXMLfromFile("perguntas.xml");
										/*if (root.getNodeType()==Node.ELEMENT_NODE && root.hasChildNodes()) {
											NodeList inputPerguntas = root.getChildNodes();
											for (int p = 0; p < inputPerguntas.getLength(); p++) {
												if(inputPerguntas.item(p).hasAttributes()) {
													Element ep = (Element) inputPerguntas.item(p);
													ep.setAttribute("id","_0"+(getlastPerguntaID()+1));
													Node imported = rootDBitems.getOwnerDocument().importNode(ep, true);
													rootDBitems.appendChild(imported);
													outputXML(rootDBitems.getOwnerDocument(),"perguntas.xml");
												}
											}
											atirar("Pergunta/s adicionada/s com sucesso.");*/
										atirar(resultado);
									}
									
								}
							}

						}
						
						
						
						//Envio :<resultado idxPergunta="0" sucesso="true/false" resp="idxResp"/>
						break;
					case "listar": // kill
						
						if(root.hasAttribute("tipo")) {
							String resposta = "";
							if(root.getAttribute("tipo").equals("perguntas")) {
								Element rootDB = readXMLfromFile("perguntas.xml");
								resposta = nodeToString(rootDB);
							}
							else {
								Element rootDB = readXMLfromFile("users.xml");
								resposta = nodeToString(rootDB);
							}
							System.out.println(resposta);
							atirar(resposta+"       ");
						}else {
							atirar("<listar result='failure'>");
						}
						
						break;
					case "kill": // kill
						System.out.println("casekill");
						atirar("Conneccao Terminada");
						fecharCanal();
						running = false;
						break;
	
					}
				}
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} // end while
	}
	
	void atirar(String out) {
		atirar(out,s);
	}
	
	void atirar(String out, Socket s) {
		try {
			os = new ObjectOutputStream(s.getOutputStream());
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
		catch (IOException | ClassNotFoundException e) {} 
		return in;
	}
	
	void fecharCanal() {
		if (s != null) {
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
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
			Document document = builder.parse(path);
			return document.getDocumentElement();

		} catch (Exception e) {
			//e.printStackTrace(System.out);
			System.out.print("Erro ao analisar o documento XML.");
		}
		return null;
	}
	
	
	
	
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
	
	private static String nodeToString(Node node)
			throws TransformerException
			{
			    StringWriter buf = new StringWriter();
			    Transformer xform = TransformerFactory.newInstance().newTransformer();
			    xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			    xform.transform(new DOMSource(node), new StreamResult(buf));
			    return(buf.toString());
			}
	

	
}
