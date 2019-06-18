<%@page import="org.w3c.dom.Document"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="tp1.server_client_communication.ClienteUDP"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Responder Grupo:<%=session.getAttribute("groupID")%></title>
</head>

<body>

	<div id="respostaAnt" name="respostaAnt"> <%= request.getAttribute("respostaAnterior") != null ? "A resposta anterior estava:" + request.getAttribute("respostaAnterior") : "" %></div>
	<%
		String titulo = "";
		Element pergunta = (Element) session.getAttribute("pergunta");
		ClienteUDP c = (ClienteUDP) session.getAttribute("client_obj");
		String numeroAluno = (String) session.getAttribute("numero");
		String idxPergunta = (String) session.getAttribute("idxPergunta");
		session.setAttribute("idxPergunta", pergunta.getAttribute("id"));
		NodeList texto = pergunta.getElementsByTagName("texto");
		int timer = Integer.valueOf(pergunta.getAttribute("duracao"));
		for (int i = 0; i < texto.getLength(); i++) {
			if (texto.item(i).getTextContent() != null && texto.item(i).getTextContent() != "") {
				titulo = texto.item(i).getTextContent();
			}
		}
	%>
	<div id="pergunta" name="pergunta">
		<form action="ResponderServlet" method="post">
			<div id="resp">
				<h2><%=titulo%></h2>
				<%
					NodeList nosResp = pergunta.getElementsByTagName("resp");

					for (int i = 0; i < nosResp.getLength(); i++) {
				%>
				<p>
					<input type="radio"
						value="<%=((Element) nosResp.item(i)).getAttribute("resid")%>"
						name="resposta"><%=nosResp.item(i).getTextContent()%></p>
				<%
					}
				%>
			</div>

			<input type="submit" name="submit">
		</form>

		<p>
			Restam <span id="countdowntimer"><%=timer%> </span> Segundos para
			responder � pergunta.
		</p>

		<script type="text/javascript">
			var timeleft = <%=timer%>;
			var downloadTimer = setInterval(
					function() {
						timeleft--;
						document.getElementById("countdowntimer").textContent = timeleft;
						if (timeleft <= 0) {
							clearInterval(downloadTimer);
		<%if (timer == 0) {
				String pedido = "<resposta numeroAluno='" + numeroAluno + "' indexPergunta='" + idxPergunta
						+ "' indexResposta=''/>";
				out.print(pedido);
				c.atirar(pedido);
				
			}%>
			}
					}, 1000);
		</script>


		<div align="center"></div>
	</div>
</body>
</html>