<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="navbar navbar-static-top navbar-inverse">
	
		<div class="container">
			
			<a href="#" class="navbar-brand">Home</a>
			<a href="#" class="navbar-brand">LOGIN</a>
						
		</div>
		
	</div>
	<div class="container">
	
		<div class="row">
		
			<div class="col-md-12 jumbotron">
			
				<div class="text-center">
				
					<h2>REGISTER HERE</h2>
					
					<form class="form-horizontal" method="POST" action="<%=request.getContextPath()%>/rest/interview/mvc/userRegistration">
					
						<div class="form-group">
							<label class="col-sm-2 control-label">First Name</label>
							<div class="col-sm-9">
							<input type="name" class="form-control" name="firstName">
							</div>
						</div>
						<br>
						<br>
						<br>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">Last Name</label>
							<div class="col-sm-9">
							<input type="name" class="form-control" name="lastName">
							</div>
						</div>
						<br>
						<br>
						
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-9">
							<input type="email" class="form-control" name="email">
							</div>
						</div>
						<br>
						<br>
						
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-9">
								<input type="password" class="form-control" name="upassword">
							</div>
						</div>
						<br>
						<br>
						
						<div class="form-group">
							<label  class="col-sm-2 control-label">Phone#</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="phone">
							</div>
						</div>
						<br>
						<br>
						
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-default">Register</button>
							</div>
						</div>
						
					</form>
					
				</div>
				
			</div>	
			
		</div>
		
	</div>
</body>
</html>