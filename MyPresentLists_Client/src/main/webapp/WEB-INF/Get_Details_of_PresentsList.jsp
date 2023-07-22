<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.ArrayList" %>
    <%@page import="be.walbert.javabeans.*" %>
	<%@page import="be.walbert.javabeans.Presents_List" %>
	<%@page import="java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Details of a Presents List</title>
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
		<h1>Details of the Presents List</h1>
		<%
		    Presents_List presents_list = (Presents_List) request.getAttribute("presents_list");
		%>
	
		<p>Limit Date: <%= presents_list.getLimit_date() %></p>
		<p>Occasion: <%= presents_list.getOccasion() %></p>
		<p>Status: <%= presents_list.isState() %></p>
		<p>Owner: <%= presents_list.getOwner().getPseudo() %></p>
		<a href="CreatePresent?id=<%= presents_list.getId_list() %>">Add present</a>
		<h2>Guests:</h2>
		<ul>
		    <% for (Users guest : presents_list.getGuests()) { %>
				<li><%= guest.getPseudo() %> - <%= guest.getPseudo() %> €</li>
		    <% } %>
		</ul>
		
		<h2>Presents:</h2>
		<table>
		    <thead>
		    	<tr>
		        	<th>Name</th>
		            <th>Description</th>
		            <th>Average Price</th>
		            <th>Priority</th>
		            <th>State</th>
		            <th>Link</th>
		            <th>Image</th>
		        </tr>
		    </thead>
		    <% for (Present present : presents_list.getPresents()) { %>
		         <tr>
		            <td><%= present.getName() %></td>
		            <td><%= present.getDescription() %></td>
		            <td><%= present.getAverage_price() %> €</td>
		            <td><%= present.getPriority() %></td>
		            <td><%= present.getState() %></td>
		            <td><%= present.getLink() %></td>
		            <td>
                    <% if (present.getImage() != null) { %>
                        <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(present.getImage()) %>" alt="Present Image" />
                    <% } %>
                	</td>
                	<td><a href="UpdatePresent?id_present=<%= present.getId_present()%>">Modify</a></td>
		         </tr>
		    <% } %>
		</table>
	</body>
</html>