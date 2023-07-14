<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
    
<!DOCTYPE html>
<html>
	<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Sign In Page</title>
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
		<h1>Welcome on the Sign In Page !</h1>
		<form class="Form" action="Signin" method="POST">
	 	<div class="row align-items-stretch mb-5">
        	<div class="col-md-6">
           		<div class="form-group">
                <!-- Pseudo input-->
                	<input name= "pseudo" class="form-control" id="pseudo" type="text" placeholder="Your Pseudo *" required="required" />
                </div>
                <div class="form-group mb-md-0">
	                <!-- Password input-->
	                <input name="password" class="form-control" id="password" type="password" placeholder="Your Password *" required="required" />
             	</div>  
             	<div class="form-group mb-md-0">
	                <!-- Password input-->
	                <input name="email" class="form-control" id="email" type="email" placeholder="Your Email *" required="required" />
             	</div> 
        	</div>                        
        </div>
        <!-- Submit Button-->
       	<button class="btn btn-primary btn-xl text-uppercase" id="submitButton" type="submit">Sign In</button> 
     </form>
     <%-- Show error message --%>
		<%
			ArrayList<String> errors = (ArrayList<String>)request.getAttribute("errors");
		%>
		<div id="Errors">
			<ul>
				<% if(errors != null){
					for(String error : errors){
						out.println("<li class=\"ErrorMessage\">" + error + "</li>");
					}
				}
				%>
			</ul>
		</div>
	</body>
</html>