<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="be.walbert.javabeans.Users" %>
<%@page import="be.walbert.javabeans.Message" %>

<!DOCTYPE html>
<html>
<html>
	<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>My Guest Lists</title>
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
	<body class="Pages">
	<%
    Users user = (Users) session.getAttribute("user");
    ArrayList<Message> messages = user.getMessages();

    if (user != null && messages != null && !messages.isEmpty()) {
	%>
	<h1>Here are your messages:</h1>
	<table>
		<thead>
			<tr>
				<th>Content</th>
	            <th>Checked</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<% for (Message message : messages) { %>
	   			<tr>
	   				<td><%= message.getContent() %></td>
	   				<td>
	   					<% if (message.isChecked()) { %>
	   						<strong><%= message.isChecked() %></strong>
	   					<% } 
	   					   else { %>
	   					   	<%= message.isChecked() %>
	                        <% } %>
	                </td>
	           	</tr>
	            <% } %>
	   	</tbody>
	</table>
	<% } else { %>
	    <h2>Sorry, you don't have messages yet</h2>
	<% } %>

	</body>
</html>
