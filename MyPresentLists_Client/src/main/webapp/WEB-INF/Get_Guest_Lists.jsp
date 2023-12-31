<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="be.walbert.javabeans.Users" %>
<%@page import="be.walbert.javabeans.Presents_List" %>

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
	<body>
		<%@ include file="Navigation.jsp" %>
		<div class="Pages">
		<%
     		Users user = (Users) session.getAttribute("user");
        	ArrayList<Presents_List> presentsLists = user.getGuests_lists();

			if (user != null && presentsLists != null && !presentsLists.isEmpty()) {%>
				<h1>Here are your guest lists:</h1>
				        <table>
				            <thead>
				                <tr>
				                    <th>Limit Date</th>
				                    <th>Occasion</th>
				                    <th>State</th>
				                    <th>Owner</th>
				                </tr>
				            </thead>
				            <tbody>
				               <% for (Presents_List list : presentsLists) { %>
							    <tr>
							        <td><%= list.getLimit_date() %></td>
							        <td><%= list.getOccasion() %></td>
							        <%if(list.isState())
							        	out.println("<td>Active</td>");
							        else
							        	out.println("<td>Expired</td>");
							        	%>
							        <td><%= list.getOwner().getPseudo() %></td>
							        <td><a href="Get_Details_of_PresentsList?id=<%= list.getId_list() %>">Details</a></td>							 
 							    </tr>
							<% } %>
				            </tbody>
				        </table>
				    <% } else { %>
				        <h2>Sorry, you don't have a guests list yet</h2>
				    <% } %>
			</div>
	</body>
</html>