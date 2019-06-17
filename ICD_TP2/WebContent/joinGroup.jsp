<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<jsp:getProperty property="cliente" name="cliente"/>
<jsp:getProperty property="groupID" name="groupID"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Responder Grupo:<jsp:getProperty property="groupID" name="groupID"/></title>
</head>

<body>
	<div id="pergunta" name="pergunta"></div>
	
	<form id="responder" action="respostaServlet">
		<input type="text" name="resposta">
		
		<input type="submit" name="submit">
	</form>
</body>
</html>