package tp1.client;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JTextArea;


@WebServlet("/MessageReceiver")
public class MessageReceiverWEB extends HttpServlet implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	Client c;
	ObjectInputStream inStream;
	HttpSession session;
	String msg;
	HttpSession s;
	
	public MessageReceiverWEB(HttpSession s, Client c) {
		super();
		this.c = c;
		this.s = s;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true) {
			 msg = receberMsg();
			 setMsg(msg);
		}
		
	}
	
	


	public void setMsg(String msg) {	
		s.setAttribute("mensagem", msg);
	}
	
	public String receberMsg() {
		String in = "";

		try { 
			inStream = new ObjectInputStream(c.getCliente().getInputStream());
			in = (String) inStream.readObject(); 
			inStream.reset();
			}
		catch (IOException e) {} catch (ClassNotFoundException e) {}
		return in;
	}

}
