<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Interview App</title>
</head>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

var contextPath='<%=request.getContextPath()%>';
	function showAddQuestion(){
		$("#addQuestion").css("visibility","visible");	
	}
	
	function callAddQuestion(){
		
		$.ajax({
	        type: "GET",
	        url:contextPath+"/rest/interview/mvc/addQuestion",
	        data:{
	        	question:$("#questionEntered").val(),
	        		options:$("#optionsEntered").val(),
				answer:$("#answerEntered").val(),
	        },
	        success: function (result) {
	        	$("#message").text(result);
	        	$("#addQuestion").css("visibility","hidden");
	        },
	        error: function (xhr, status, error) {
	        	alert(xhr.responseText);
	        }
	    });
	}

	function callManageUsers(){
		$("#addQuestion").css("visibility","hidden");
		/* window.location = contextPath+"/rest/interview/mvc/showUsersList"; */
		 $.ajax({
	        type: "GET",
	        url: contextPath+"/rest/interview/mvc/showUsersList",
	        success: function (result) {
	        	var appenddata = "";
	        	for(var i = 0; i<result.userList.length;i++){
	        		appenddata =appenddata+ "<HTML><BODY><SPAN>First Name:"+result.userList[i].userFirstName+"</SPAN><BR>"+
	        		"<SPAN>Last Name:"+result.userList[i].userLastNAme+"</SPAN><BR>"+
	        		"<SPAN>Email:"+result.userList[i].userEmail+"</SPAN><BR>"
	        		+"<SPAN>phone:"+result.userList[i].phone+
	        		"</SPAN><BR><BUTTON onclick='deleteUser("+result.userList[i].userEmail+")'>Delete</BUTTON></BODY></HTML>";
	        	}
	        	$("#showUsersData").text(appenddata)
	        	$("#delete").css("visisbility","visible");
	        	$("#showUsersData").css("visibility","visible");
	     		
	        },
	        error: function (xhr, status, error) {
	        	alert(xhr.responseText);
	        }
	    }); 
		
		 function deleteUser(email){
				window.location = contextPath+"/rest/interview/mvc/deleteUser?email="+email+"";
		}
	}
</script>

<body>
	<div class="navbar navbar-static-top navbar-inverse">
	
		<div class="container">
		
			<a href="#" class="navbar-brand">HOME</a>
			<a href="#" class="navbar-brand">Profile</a>
						
		</div>
		
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-12 jumbotron">
				<div class="text-center">
					<h1>Hello<%-- ${it.result})--%></h1> 
					<span id="message" style="color: red"></span>
						<div class="form-group">
								<label class="col-sm-2 control-label">Click below to add a new question</label>
							<div class="col-sm-10">
								<button onclick="showAddQuestion()">Add Question</button>
							</div>
						</div>
						<br>
						<div class="form-group" id="addQuestion" style="visibility: hidden;">
								Question: <input type="text" id="questionEntered"/>
							<div class="col-sm-10">
								Options: <input type="text" id="optionsEntered"/>
							</div>
							<div class="col-sm-10">
								Answer: <input type="text" id="answerEntered"/>
							</div>
							<div class="col-sm-10">
								<button onclick="callAddQuestion()">Add Question</button>
							</div>
						</div>
						
						<br>
						<div class="form-group" id="manageUsers">
							<label class="col-sm-2 control-label">Click below to manage Users</label>
							<div class="col-sm-10">
								<button onclick="callManageUsers()">Manage Users</button>
							</div>
						</div>
						
						<div>
							<span id="showUsersData" style="visibility: hidden;">
								
							</span>
							<span id="delete" style="visibility: hidden;">
								<button onclick="deleteUser()">Delete</button>
							</span>
						</div>
				</div>
			</div>		
		</div>
	</div>
</body>
</html>