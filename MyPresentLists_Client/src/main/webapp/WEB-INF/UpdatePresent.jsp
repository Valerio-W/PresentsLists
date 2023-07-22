<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="be.walbert.javabeans.Present"%>
    <%@ page import="java.util.ArrayList" %>
    
<!DOCTYPE html>
<html>
	<head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Update Present</title>
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
		Present present = (Present) session.getAttribute("present");
	%>
		<h1>Update a Present</h1>
		<form class="Form" action="UpdatePresent" method="POST" enctype="multipart/form-data"><!-- to allow the sending of files. -->
	 	<div class="row align-items-stretch mb-5">     
        	<h3>New Present</h3>
        	<div class="col-md-6"><!-- First present to add -->
           		<div class="form-group">
                	<!-- Name input-->
                	<input name= "name" class="form-control" id="name" type="text" 
                	placeholder="<%=present.getName() %>" required="required" value="<%=present.getName() %>" />
                </div>
               <div class="form-group mb-md-0">
                    <!-- Description input -->
                    <input name="description" class="form-control" id="description" type="text"
                        placeholder="<%= present.getDescription() %>" required="required"
                        value="<%= present.getDescription() %>" />
                </div>
                <div class="form-group mb-md-0">
                    <!-- Average Price input -->
                    <input name="average_price" class="form-control" id="average_price" type="number" step="0.01"
                        placeholder="<%= present.getAverage_price() %>" required="required"
                        value="<%= present.getAverage_price() %>" />
                </div>
             	<div class="form-group mb-md-0">
			    <!-- Priority input -->
			    <select name="priority" class="form-control" id="priority" required="required">
			        <option value="" disabled selected>Priority *</option>
			        <% for(int i = 1; i <= 10; i++) {
			            if (i == present.getPriority()) {
			                out.println("<option value=\"" + i + "\" selected>" + i + "</option>");
			            } else {
			                out.println("<option value=\"" + i + "\">" + i + "</option>");
			            }
			        } %>
			    </select>
				</div>
             	<div class="form-group mb-md-0">
                    <!-- Link input -->
                    <input name="link" class="form-control" id="link" type="text"
                        placeholder="<%= present.getLink() %>" required="required"
                        value="<%= present.getLink() %>" />
                </div>
             	<div class="form-group mb-md-0">
				    <!-- Image input -->
				    <input name="image" class="form-control" id="image" type="file" accept="image/*"/>
				</div>
        	</div>                       
        </div>
        <!-- Submit Button-->
		<button class="btn btn-primary btn-xl text-uppercase" id="submit" name="submit" type="submit">Update</button>
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