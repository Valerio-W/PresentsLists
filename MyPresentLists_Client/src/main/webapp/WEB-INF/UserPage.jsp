<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="be.walbert.javabeans.Users"  %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>My Page</title>
	</head>
	<body>
		<%
			Users u = (Users) session.getAttribute("user");
		%>
		<h1>Welcome on your page <%=u.getPseudo() %></h1>
	</body>
</html>