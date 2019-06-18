package controllers;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import tp1.server_client_communication.ClienteUDP;

@WebServlet("/ListarServlet")
public class ListarServlet extends HttpServlet{
	
private ClienteUDP cliente;
	
	private static final long serialVersionUID = 1L;
	
	public ListarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		String tipo = request.getParameter("tipo");  
		HttpSession session = request.getSession();
		cliente = (ClienteUDP) session.getAttribute("cliente_obj");
		String pedido="";
		if (tipo.equals("perguntas")) {
			pedido = "<listar tipo='perguntas'/>";
			session.setAttribute("listatipo", "perguntas");
		}
		else {
			pedido = "<listar tipo='alunos'/>";
			session.setAttribute("listatipo", "alunos");
		}
		cliente.atirar(pedido);
		String resposta = cliente.apanhar();
		Element resp = readXML(resposta);
		System.out.println(resposta);
		session.setAttribute("lista", resp);
		getServletContext().getRequestDispatcher("/perguntar.jsp").forward(request,response);
		
    }  
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doPost(req, resp);  
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
