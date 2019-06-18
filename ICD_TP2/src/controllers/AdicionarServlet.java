package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import tp1.server_client_communication.ClienteUDP;

/**
 * Servlet implementation class addPerguntaServlet
 */
@WebServlet("/AdicionarServlet" )
public class AdicionarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClienteUDP cliente = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdicionarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		cliente = (ClienteUDP) session.getAttribute("cliente_obj");
		String Tema=request.getParameter("Tema");  
        String Pergunta = request.getParameter("Pergunta");
        String Duracao = request.getParameter("Duracao");
        String RespostasPossiveis = request.getParameter("RespostasPossiveis");
        
        if(Tema==null||Tema.isEmpty()||Pergunta==null||Pergunta.isEmpty()||Duracao==null||Duracao.isEmpty()||RespostasPossiveis==null||RespostasPossiveis.isEmpty()) {
        	request.setAttribute("erro", "É necessario preencher todos os campos");
        }else {
        	String msg = "<adicionarPergunta><pergunta duracao='"+Duracao+"' tema='"+Tema+"'>" + 
        			"		<texto>"+Pergunta+"</texto>" + 
        			"		<anexoMultimedia></anexoMultimedia>" + 
        			"		<respPossiveis>";
        	String[] respostas = RespostasPossiveis.split("/");
        	for (int i = 0; i < respostas.length; i++) {
        		 
            			msg += "<resp resid='"+i+"' valid='"+respostas[i].split(",")[1]+"'>"+respostas[i].split(",")[0]+"</resp>";
            			
			}
        	msg += "</respPossiveis></pergunta></adicionarPergunta>";
        	cliente.atirar(msg);
        	String resposta = cliente.apanhar();
        	request.setAttribute("adicionaPergunta", resposta);
        	getServletContext().getRequestDispatcher("/adicionar.jsp").forward(request,response);
        }
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
