<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Jogo das Perguntas</title>
</head>
<body>
	<h1>Para aceder ao servi�o � necess�rio o login:</h1>
	<form id="login" action="LoginServlet" method="post">
		<p>Numero: <input type="text" name="numero"></p>
		
		<p>Professor <input type="checkbox" value="true" name="prof"></p>
		<input type='hidden' value='false' name='prof'>
		
		<p><input type="submit" name="submit"></p>
		 
	</form>
</body>
</html>