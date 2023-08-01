<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="be.walbert.javabeans.Users" %>
<%@page import="be.walbert.javabeans.Present" %>
<%@page import="java.util.Base64" %>
    
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Offer a present</title>
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
		    <% Present present = (Present) session.getAttribute("present"); %>
		
		    <% if (present != null) { %>
		        <h1>Present informations :</h1>
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
				    <tr>
				    	<td><%= present.getName() %></td>
				        <td><%= present.getDescription() %></td>
				        <td><%= present.getAverage_price() %> €</td>
				        <td><%= present.getPriority() %></td>
				        <td><%= present.getState() %></td>
				        <td><%= present.getLink() %></td>
				        <% if (present.getImage() != null) { %>
		                        <td><img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(present.getImage()) %>" alt="Present Image" /></td>
		                <% } %>
				    </tr>
				</table>
		    <% }%>
		
		    <% if (present != null) { %>
		        <h2>You have two options:</h2>/
		       <form action="OfferPresent" method="post">
				    <label>
				        <input type="radio" name="offerOrPayment" value="offer" required> Offer the present
				    </label>
				    <br>
				    <label>
				        <input type="radio" name="offerOrPayment" value="payment" required> Make multiple payment
				    </label>
				    <br>
				    <label for="pricePaid">If you choose Multiple Payment, indicate Price Paid:</label>
				    <input type="number" name="price_paid" step="0.01" id="price_paid"><br>
				    <input type="submit" value="Submit">
				</form>
		    <% }%>    
		    <%
		    	if ((String) request.getSession().getAttribute("error_price_paid") != null) {
				  out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("error_price_paid") + "</div>");
				  request.getSession().removeAttribute("error_price_paid");
				}
			%>
			<%
		    	if ((String) request.getSession().getAttribute("error_present_already_fullpaid") != null) {
				  out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("error_present_already_fullpaid") + "</div>");
				  request.getSession().removeAttribute("error_present_already_fullpaid");
				}
			%>
			<%
		    	if ((String) request.getSession().getAttribute("already_reserved") != null) {
				  out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("already_reserved") + "</div>");
				  request.getSession().removeAttribute("already_reserved");
				}
			%>
			<%
		    	if ((String) request.getSession().getAttribute("error_already_multiple_payments") != null) {
				  out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("error_already_multiple_payments") + "</div>");
				  request.getSession().removeAttribute("error_already_multiple_payments");
				}
			%>
			<%
		    	if ((String) request.getSession().getAttribute("error_price_paid_empty") != null) {
				  out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("error_price_paid_empty") + "</div>");
				  request.getSession().removeAttribute("error_price_paid_empty");
				}
			%>
		</div>
	</body>
</html>