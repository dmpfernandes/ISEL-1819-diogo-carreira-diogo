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

/**
 * Servlet implementation class ResponderServlet
 */
@WebServlet("/ResponderServlet")
public class ResponderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResponderServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// <resposta numeroAluno="12346" indexPergunta="5" indexResposta="0"/>
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		ClienteUDP c = (ClienteUDP) session.getAttribute("cliente_obj");
		String numeroAluno = (String) session.getAttribute("numero");
		String idxPergunta = (String) session.getAttribute("idxPergunta");
		String resposta = (String) request.getParameter("resposta");

		String pedido = "<resposta numeroAluno='" + numeroAluno + "' indexPergunta='" + idxPergunta
				+ "' indexResposta='" + resposta + "'/>";
		System.out.println(pedido);
		c.atirar(pedido);
		String msg = c.apanhar();
		if (msg.contains("group")) {
			Element e = readXML(msg);
			String group = e.getAttribute("group");
			c.joinGroup(group);
			String pergCompleta = c.apanharMulti();
			c.leaveGroup(group);
			Element pergCompletaElem = (Element) readXML(pergCompleta);
			String soTxt = pergCompleta.split("<respPossiveis>")[0] + " </pergunta>";
			session.setAttribute("pergunta", pergCompletaElem);
			session.setAttribute("titulo", soTxt);
		} else if (msg.contains("sucesso")) {
			String rspEnviada = msg.split("sucesso='")[1];
			String rspFinal = rspEnviada.split("'")[0];
				if (rspFinal.equals("true")) {
					request.setAttribute("respostaAnterior", "Correta.");
				} else if (rspFinal.equals("false")) {
					request.setAttribute("respostaAnterior", "Errada.");
				}

		}
		getServletContext().getRequestDispatcher("/responder.jsp").forward(request, response);

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
