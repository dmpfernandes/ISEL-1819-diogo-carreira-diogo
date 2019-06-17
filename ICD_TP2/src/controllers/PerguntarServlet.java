package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tp1.server_client_communication.ClienteUDP;

@WebServlet("/PerguntarServlet")
public class PerguntarServlet extends HttpServlet {
	
	private ClienteUDP cliente;
	
	private static final long serialVersionUID = 1L;
	
	public PerguntarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
          
        String idPergunta=request.getParameter("idPergunta");  
        String Todos = request.getParameter("Todos");
        String alunosSelecionados = "";
        if(Todos==null) {
        	alunosSelecionados = request.getParameter("alunosSelecionados");
        }
        
        boolean ok = false;
        String userPermit = "";
        
        if(idPergunta == null || (Todos==null&& alunosSelecionados.equals(""))){
			request.setAttribute("erro", "Tem de preencher todos os campos!!!");
		}else{
			HttpSession session = request.getSession();
			cliente = (ClienteUDP) session.getAttribute("cliente_obj");
	        String msg = "<selecionarPergunta index='"+idPergunta+"' todos='"+(Todos!=null?"'/>":"'>");
	        if(Todos==null) {
	        	String[] alunos = alunosSelecionados.split(",");
	        	for (int i = 0; i < alunos.length; i++) {
					msg += "<aluno numero='"+alunos[i]+"'/>";
				}
	        	msg += "</selecionarPergunta>";
	        }
	        cliente.atirar(msg);
	        String resposta = cliente.apanhar();
	        
	        //"<pergunta index="+idPergunta+">Pergunta Selecionada com Sucesso</pergunta>"
	        if(resposta.contains("success")) {
	        	userPermit = "success";
	        	ok = true;
			}
	        else if(resposta.contains("failure")) {
	            userPermit="failure";
	            ok = true;
	        }
			else {
				request.setAttribute("erro", "Credenciais inválidas!");
			}
			
		}
        
        if(ok) {
			if("success".equals(userPermit)) {
				getServletContext().getRequestDispatcher("/homeProf.jsp").forward(request,response);
			}
			if("failure".equals(userPermit)) {
				getServletContext().getRequestDispatcher("/perguntar.jsp").forward(request,response);
			}
			if("".equals(userPermit)) {
				getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
			}
			
		}
		else {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
		}
        
        
        
        
        
        
              
    }  
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doPost(req, resp);  
    }  
	
}
