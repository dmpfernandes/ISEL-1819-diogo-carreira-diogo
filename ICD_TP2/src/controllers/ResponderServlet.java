package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		<resposta numeroAluno="12346" indexPergunta="5" indexResposta="0"/>
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		ClienteUDP c = (ClienteUDP)session.getAttribute("cliente_obj"); 
		String numeroAluno = (String) session.getAttribute("numero");
		String idxPergunta = (String) session.getAttribute("idxPergunta");
		String resposta = (String) request.getParameter("resposta");

		String pedido = "<resposta numeroAluno='" + numeroAluno + "' indexPergunta='" + idxPergunta +"' indexResposta='" + resposta +"'/>";
		System.out.println(pedido);
		c.atirar(pedido);
	}

}
