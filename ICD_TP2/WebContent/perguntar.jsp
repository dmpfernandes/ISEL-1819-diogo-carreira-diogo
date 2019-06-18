<%@ page import="org.w3c.dom.Document"%>
<%@ page import="org.w3c.dom.Element"%>
<%@ page import="org.xml.sax.InputSource"%>
<%@ page import="org.w3c.dom.NamedNodeMap"%>
<%@ page import="org.w3c.dom.Node"%>
<%@ page import="org.w3c.dom.NodeList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Selecionar Pergunta</title>
</head>
<body>
	<div>
		<h1>Selecionar Perguntar</h1>
	
		<form id="perguntar" action="PerguntarServlet" method="post">
			<div>
				<p>Indique o id da pergunta a selecionar: <input type="text" name="idPergunta"></p>
				
				<p><input type="checkbox" name="Todos" value="true"></p>
				
				<p>Indique o/s nº de aluno/s a selecionar (separados por ","): <input type="text" name="alunosSelecionados"></p>
				
				<p><input type="submit" name="submit"></p>
			</div>
		</form>
	</div>
	<div>
	<h1>Listar</h1>
		<form id="lista" action="ListarServlet" method="post">
			<div>				
				<p><input type="radio" name="tipo" value="perguntas" checked>Perguntas</p>
				<p><input type="radio" name="tipo" value="alunos">Alunos</p>	
				<p><input type="submit" name="submit"></p>
			</div>
			<div>
				<% 
				Element lista = (Element) session.getAttribute("lista");
				String listatipo = (String) session.getAttribute("listatipo");
				if(lista != null && listatipo != null){
					if (listatipo.equals("perguntas")){
						NodeList l = lista.getElementsByTagName("pergunta");
						for(int i = 0; i< l.getLength();i++){
							String elem = ((Element) l.item(i)).getElementsByTagName("texto").item(0).getTextContent();
							%>
							<p> <%= i %>. <%= elem%> </p><% 
						}
					}
					if (listatipo.equals("alunos")){
						NodeList l = lista.getElementsByTagName("user");
						for(int i = 0; i< l.getLength();i++){
							String elemNome = ((Element) l.item(i)).getElementsByTagName("nome").item(0).getTextContent();
							String elemNumero = ((Element) l.item(i)).getElementsByTagName("numero").item(0).getTextContent();
							%>
							<p> <%= i %>. <%= elemNome%> nº <%= elemNumero%> </p><% 
						}
					}
					
			
				}
				
				
				%>
			</div>
		</form>
		
		
	</div>
	
	
	
</body>
</html>