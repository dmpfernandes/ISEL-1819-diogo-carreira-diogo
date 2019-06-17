package controllers;  
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;


import tp1.server_client_communication.ClienteUDP;  
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {  
    /**
	 * Servlet de Login inicial
	 */
	
	private ClienteUDP cliente;
	
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
          
        String numero=request.getParameter("numero");  
        String isProf = request.getParameter("prof");
        boolean ok = false;
        String userPermit = "";
        
        if(numero == null || isProf == null || numero.equals("")|| isProf.equals("")){
			request.setAttribute("erro", "Tem de preencher todos os campos!!!");
		}else{
			
			cliente = new ClienteUDP();
	        String msg = "<login numero='" + numero + "' tipo='" + (isProf.equals("true") ? "prof" : "aluno") + "'/>";
	        cliente.atirar(msg);
	        String resposta = cliente.apanhar();
	        HttpSession session = request.getSession();
			
	        if(resposta.contains("true")) {

				if(session.getAttribute("cliente_obj")!=null)
					session.setAttribute("cliente_obj", null);
				session.setAttribute("cliente_obj", cliente);
				session.setAttribute("numero", numero);
				userPermit="prof";
				ok = true;
				
			}
	        else if(resposta.contains("group")) {
	        	String groupID = resposta.split("'")[1];
	        	if(session.getAttribute("groupID")!=null)
					session.setAttribute("groupID", null);
	        	session.setAttribute("groupID", groupID);
	            userPermit="aluno";
	            ok = true;
	        }
			else {
				request.setAttribute("erro", "Credenciais inválidas!");
			}
			
		}
        
        if(ok) {
			if("prof".equals(userPermit)) {
				getServletContext().getRequestDispatcher("/homeProf.jsp").forward(request,response);
			}
			if("aluno".equals(userPermit)) {
				getServletContext().getRequestDispatcher("/responder.jsp").forward(request,response);
			}
			if("admin".equals(userPermit)) {
				getServletContext().getRequestDispatcher("/homeAdmin.jsp").forward(request,response);
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
