<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Home Page</title>
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
	<meta charset="UTF-8">
	<title>Create Presents List</title>
	</head>
	<body class="Pages">
		<h1>Create a new Presents List !</h1>
		<form class="Form" action="CreatePresents_List" method="POST">
	 	<div class="row align-items-stretch mb-5">
        	<div class="col-md-6">
        	<h3>New List</h3>
           		<div class="form-group">
                <!-- limit_date input-->
                   	<input name= "limit_date" class="form-control" id="limit_date" type="date" placeholder="Limit Date *" required="required" />
                </div>
                <div class="form-group mb-md-0">
	            <!-- occasion input-->
	            	<input name="occasion" class="form-control" id="occasion" type="text" placeholder="Occasion *" required="required" />
             	</div>  
        	</div>         
        	<h3>First Present</h3>
        	<div class="col-md-6"><!-- First present to add -->
           		<div class="form-group">
                	<!-- Name input-->
                	<input name= "name" class="form-control" id="name" type="text" placeholder="Name *" required="required" />
                </div>
                <div class="form-group mb-md-0">
	                <!-- Description input-->
	                <input name="description" class="form-control" id="description" type="text" placeholder="Description *" required="required" />
             	</div>  
             	<div class="form-group mb-md-0">
	                <!-- Average Price input-->
	                <input name="average_price" class="form-control" id="average_price" type="number" step="0.01" placeholder="Average Price*" required="required" />
             	</div> 
             	<div class="form-group mb-md-0">
	                <!-- Priority input -->
				    <select name="priority" class="form-control" id="priority" required="required">
				        <option value="" disabled selected>Priority *</option>
				        <% for(int i=1;i<11;i++)
							out.println("<option value=\"" + i +"\">"+i+ "</option>");
				        %>
				    </select>
             	</div> 
             	<div class="form-group mb-md-0">
	                <!-- Link input-->
	                <input name="link" class="form-control" id="link" type="text" placeholder="Link *" />
             	</div> 
             	<div class="form-group mb-md-0">
				    <!-- Image input -->
				    <input name="image" class="form-control" id="image" type="file" accept="image/*"/>
				</div>
        	</div>                       
        </div>
        <!-- Submit Button-->
       	<button class="btn btn-primary btn-xl text-uppercase" id="submitButton" type="submit">Create</button> 
     </form>
	</body>
</html>