<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Update PresentsList limit date</title>
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
	<form class="Form" action="UpdatePresentsList" method="POST"><!-- to allow the sending of files. -->
	 	<div class="row align-items-stretch mb-5">
        	<div class="col-md-6">
        	<h3>Update limit date</h3>
           		<div class="form-group">
                <!-- limit_date input-->
                   	<input name= "limit_date" class="form-control" id="limit_date" type="date" placeholder="Limit Date *" required="required" />
                </div>
        	</div>                          
        </div>
        <!-- Submit Button-->
		<button class="btn btn-primary btn-xl text-uppercase" id="submit" name="submit" type="submit">Create</button>
     </form>
     <%
		  if ((String) request.getSession().getAttribute("errorLimitDate") != null) {
		    out.println("<div class=\"ErrorMessage\">" + (String) request.getSession().getAttribute("errorLimitDate") + "</div>");
		    request.getSession().removeAttribute("errorLimitDate");
		  }
	 %>
</body>
</html>