<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="be.walbert.javabeans.Presents_List" %>
<!DOCTYPE html>
<html>
	<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>My Presents Lists</title>
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
    		ArrayList<Presents_List> presentsLists = (ArrayList<Presents_List>) request.getAttribute("presentsLists");
		%>
<div>
    <% if (presentsLists != null && !presentsLists.isEmpty()) { %>
        <h1>Here are your present lists:</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Limit Date</th>
                    <th>Occasion</th>
                    <th>State</th>
                </tr>
            </thead>
            <tbody>
               <% for (Presents_List list : presentsLists) { %>
			    <tr>
			        <td><%= list.getId_list() %></td>
			        <td><%= list.getLimit_date() %></td>
			        <td><%= list.getOccasion() %></td>
			        <%if(list.isState())
			        	out.println("<td>Active</td>");
			        else
			        	out.println("<td>Expired</td>");
			        	%>
			    	<td><a href="Get_Details_of_PresentsList?id=<%= list.getId_list() %>">Consult</a></td>
			    </tr>
			<% } %>
            </tbody>
        </table>
    <% } else { %>
        <h2>Sorry, you don't have a presents list yet</h2>
    <% } %>
</div>
 		<% 
     	if ((String) request.getSession().getAttribute("confirm_New_Present") != null) {
		  out.println("<div class=\"ConfirmationMessage\">" + (String) request.getSession().getAttribute("confirm_New_Present") + "</div>");
		  request.getSession().removeAttribute("confirm_New_Present");
		}
		%>
	</body>
</html>