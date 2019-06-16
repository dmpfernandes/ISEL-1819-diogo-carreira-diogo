package controllers;  
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


import tp1.server_client_communication.Client;  
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {  
    /**
	 * Servlet de Login inicial
	 */
	
	private Client cliente;
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        String numero=request.getParameter("numero");  
        boolean status = false;
        boolean prof = request.getParameter("prof").equals(null) ? false : true;
        cliente = new Client();
        String msg = "<login numero='" + numero + "' tipo='" + (prof ? "prof" : "aluno") + "'/>";
        cliente.atirar(msg);
        request.setAttribute("cliente", cliente);
        String resposta = cliente.apanhar();
        if(resposta.contains("true")) {
        	RequestDispatcher rd=request.getRequestDispatcher("login-success.jsp");  
            rd.forward(request, response);  
        } else if(resposta.contains("group")) {
        	String groupID = resposta.split("'")[1];
        	request.setAttribute("groupID", groupID);
        	RequestDispatcher rd=request.getRequestDispatcher("joinGroup.jsp");  
            rd.forward(request, response);
        }
//        loginController bean=new loginController(name, password);  
//        bean.setName(name);  
//        bean.setPassword(password);  
//        request.setAttribute("bean",bean);  
          
//        boolean status=bean.validate();  
              
    }  
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doPost(req, resp);  
    }  
}  
