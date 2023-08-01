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
	<body>
		<%@ include file="Navigation.jsp" %>
		<div class="Pages">
			<h1>Details of the Presents List</h1>
			<%
			    Presents_List presents_list = (Presents_List) session.getAttribute("presents_list");
				Users u = (Users) session.getAttribute("user");
			%>
		
			<p>Limit Date: <%= presents_list.getLimit_date() %></p>
			<p>Occasion: <%= presents_list.getOccasion() %></p>
			<p>Status: <%= presents_list.isState() %></p>
			<p>Owner: <%= presents_list.getOwner().getPseudo() %></p>
			
			<% if (presents_list.getOwner().getId() == u.getId()) { %>
		    <p>
		        <a href="UpdatePresentsList?id=<%= presents_list.getId_list() %>&action=<%= presents_list.isState() ? "disable" : "enable" %>">
		            <%= presents_list.isState() ? "Disable List" : "Enable List" %>
		        </a>
		    </p>
			<% } %>
	
			<% if (!presents_list.isState() && presents_list.getOwner().getId()== u.getId()) { %>
			    <p><a href="UpdatePresentsList?id=<%= presents_list.getId_list() %>">Update limit_date</a></p>
			<% } %>
			<% if(presents_list.getOwner().getId()== u.getId()){ %>
			<a href="CreatePresent?id=<%= presents_list.getId_list() %>">Add present</a>
			<a href="AddGuests?id=<%= presents_list.getId_list() %>">Add guest</a>
			<%} %>
			<h2>Guests:</h2>
			<ul>
			    <% for (Users guest : presents_list.getGuests()) { %>
			        <li><%= guest.getPseudo() %></li>
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
	                	<%if(present.getState().equalsIgnoreCase("ordered") && presents_list.getOwner().getId()== u.getId()){ %>
	                	<td><a href="UpdatePresent?id_present=<%= present.getId_present()%>">Modify</a></td>
	                	<%} %>
	                	<%if(present.getState().equalsIgnoreCase("ordered") && presents_list.getOwner().getId() != u.getId() && presents_list.isState()){ %>
	                	<td><a href="OfferPresent?id_present=<%= present.getId_present()%>">Offer</a></td>
	                	<%} %>
	                	<%if((present.getState().equalsIgnoreCase("reserved") || present.getState().equalsIgnoreCase("fully paid")) && presents_list.isOwnerOrGuest(u)){ %>
	                	<td><a href="Get_Details_of_Present?id_present=<%= present.getId_present()%>">Details</a></td>
	                	<%} %>
			         </tr>
			    <% } %>
			</table>
			<%
			  if ((String) request.getSession().getAttribute("error_list_disable") != null) {
			    out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("error_list_disable") + "</div>");
			    request.getSession().removeAttribute("error_list_disable");
			  }
			%>
		</div>
	</body>
</html>