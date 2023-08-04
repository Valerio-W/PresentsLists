<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="be.walbert.javabeans.Users" %>
<%@page import="be.walbert.javabeans.Present" %>
<%@page import="be.walbert.javabeans.Multiple_Payment" %>
<%@page import="java.util.Base64" %>
<%@page import="java.util.ArrayList" %>

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
				        <td>
							<%if(present.getLink()!=null){ %>
								<a href="<%= present.getLink() %>"><%= present.getLink() %></a>
							<%} else{%>
								No link
							<%} %></td>
				             <td><% if (present.getImage() != null) { %>
				             	<div style="width: 200px; height: 200px;">
									<img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(present.getImage()) %>" alt="Present Image" style="max-width: 100%; max-height: 100%;" />
								</div>
							<% } else{%>
								No Image
							<%} %>
	            		</td>
				    </tr>
				</table>
		    <% }%>
			 <h2>Payments :</h2>
		    <table>
		        <thead>
		            <tr>
		                <th>Price Paid</th>
		                <th>User ID</th>
		            </tr>
		        </thead>
		        <tbody>
		            <% for (Multiple_Payment payment : present.getPayments()) { %>
		                <tr>
		                    <td><%= payment.getPrice_paid() %> €</td>
		                    <td><%= payment.getUser().getPseudo() %></td>
		                </tr>
		            <% } %>
		        </tbody>
		    </table>
		    <% if (present != null && present.getState().equals("ordered") && present.getPayments().size()==0) { %>
		        <h2>You have two options:</h2>
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
		    <% }else{%>   
		     	<form action="OfferPresent" method="post">
				    <label>
				        <input type="radio" name="offerOrPayment" value="payment" required> Make multiple payment
				    </label>
				    <br>
				    <label for="pricePaid">Indicate Price Paid:</label>
				    <input type="number" name="price_paid" step="0.01" id="price_paid"><br>
				    <input type="submit" value="Submit">
				</form>
		    <%} %> 
		   <% ArrayList<String> errors = (ArrayList<String>) request.getAttribute("errors"); %>
			<% if (errors != null && !errors.isEmpty()) { %>
			    <div class="alert alert-danger" role="alert">
			        <ul>
			            <% for (String error : errors) { %>
			                <li><%= error %></li>
			            <% } %>
			        </ul>
			    </div>
			<% } %>
		</div>
	</body>
</html>