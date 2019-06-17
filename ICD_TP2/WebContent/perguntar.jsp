<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Selecionar Pergunta</title>
</head>
<body>
	<h1>Selecionar Perguntar</h1>
	
	<form id="login" action="PerguntarServlet" method="post">
		<p>Indique o id da pergunta a selecionar: <input type="text" name="idPergunta"></p>
		
		<p><input type="checkbox" name="Todos" value="true"></p>
		
		<p>Indique o/s nº de aluno/s a selecionar (separados por ","): <input type="text" name="alunosSelecionados"></p>
		
		<p><input type="submit" name="submit"></p>
	</form>
	
	
	
	
</body>
</html>