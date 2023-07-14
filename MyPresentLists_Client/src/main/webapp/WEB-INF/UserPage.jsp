<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="be.walbert.javabeans.Users"  %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Home Page</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="./assets/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="./css/styles.css" rel="stylesheet" />
    </head>
	<body>
		<%
			Users u = (Users) session.getAttribute("user");
		%>
		<h1>Welcome on your page <%=u.getPseudo() %></h1>
		<a href="CreatePresents_List">Create Presents List</a>
		<% 
		  if ((String) request.getSession().getAttribute("confirm_New_Presents_List") != null) {
		    out.println("<div class=\"ConfirmationMessage\">" + (String) request.getSession().getAttribute("confirm_New_Presents_List") + "</div>");
		    request.getSession().removeAttribute("confirm_New_Presents_List");
		  }
		%>
		<%
		  if ((String) request.getSession().getAttribute("alreadyConnected") != null) {
		    out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("alreadyConnected") + "</div>");
		    request.getSession().removeAttribute("alreadyConnected");
		  }
		%>
	</body>
</html>