<%@ page import="org.w3c.dom.Document"%>
<%@ page import="org.w3c.dom.Element"%>
<%@ page import="org.xml.sax.InputSource"%>
<%@ page import="org.w3c.dom.NamedNodeMap"%>
<%@ page import="org.w3c.dom.Node"%>
<%@ page import="org.w3c.dom.NodeList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adicionar Pergunta</title>
</head>
<style>
	#Pergunta, select {
	  width: 40%;
	  height: 10%;
	  padding: 12px 20px;
	  margin: 8px 0;
	  
	  overflow:scroll;
	}
</style>
<body>

<div>
		<h1>Adicionar Pergunta</h1>
	
		<form id="perguntar" action="AdicionarServlet" method="post">
			<div>
				<p>Tema da Pergunta: <input id="Tema" type="text" name="Tema"></p>
				<p>Corpo da Pergunta: </p><textarea id="Pergunta" name="Pergunta" rows="10" cols="50"></textarea>
				<p>Duração da Pergunta em segundos: <input type="text" name="Duracao"></p>
				<p>Indique as Respostas possiveis (resposta,validade/resposta,validade):</p><textarea id="RespostasPossiveis" name="RespostasPossiveis" rows="10" cols="50">ex: respPossivel,true/anotherRespPossivel,false</textarea>

				<p><input type="submit" name="submit"></p>
				
				<%= request.getAttribute("adicionaPergunta")==null?"":request.getAttribute("adicionaPergunta") %>
				<%= request.getAttribute("erro")==null?"":request.getAttribute("erro") %>
			</div>
		</form>
	</div>

</body>
</html>