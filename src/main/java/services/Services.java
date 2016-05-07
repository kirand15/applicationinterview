package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

import com.fasterxml.jackson.databind.ObjectMapper;

import command.AdminCommand;
import command.FeedbackCommand;
import command.QuestionDriveCommand;
import command.QuestionsMaintanence;
import command.UserValidateCommand;
import model.Questions;
import model.userModel;

@Path("interview")
public class Services {
	ObjectMapper mapper = new ObjectMapper();
	
	@GET
	@Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
	
	@POST
	@Path("mvc/userValidate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response userValidate(@FormParam("userEmail") String userEmail,
			@FormParam("password") String password) {
		Response response;
		userModel usermodel = new userModel();
		usermodel.setUserEmail(userEmail);;
		usermodel.setPassword(password);
		
		String result;
		UserValidateCommand userValidateCommand = new UserValidateCommand();
		usermodel = userValidateCommand.validateUser(usermodel);
		if(usermodel == null){
			result = "Invalid Username/Password";
			response = Response.ok(new Viewable("/userLogin.jsp",result)).build();
			
		}else if(usermodel.getRole() != null && usermodel.getRole().trim().equalsIgnoreCase("user")){
			result = usermodel.getUserFirstName();
			response = Response.ok(new Viewable("/displayQuestions.jsp",result)).build();
		}else if(usermodel.getRole() != null && usermodel.getRole().trim().equalsIgnoreCase("admin")){
			result = usermodel.getUserFirstName();
			response = Response.ok(new Viewable("/admin.jsp",result)).build();
		}else{
			response = Response.ok(new Viewable("/userRegistration.jsp","")).build();
		}
		return response;
	}
	
	@POST
	@Path("mvc/userRegistration")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response userRegistration(@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("upassword") String password,
			@FormParam("email") String email,
			@FormParam("phone") String phone) {
		
		Response response;
		userModel usermodel = new userModel();
		usermodel.setUserFirstName(firstName);
		usermodel.setUserLastNAme(lastName);
		usermodel.setPassword(password);
		usermodel.setUserEmail(email);
		usermodel.setPhone(phone);
		UserValidateCommand userValidateCommand = new UserValidateCommand();
		String result = userValidateCommand.userRegistration(usermodel);
		response = Response.ok(new Viewable("/userLogin.jsp",result)).build();
			
		return response;
	}
	
	@POST
	@Path("mvc/getQuestions")
	@Produces(MediaType.TEXT_PLAIN)
	public String driveQuestions() {
		
		Questions questions = new Questions();
		
		QuestionDriveCommand questionDriveCommand = new QuestionDriveCommand();
		questions = questionDriveCommand.getQuestion();
		
		String questionData = questions.getQuestion() +"-"+ questions.getChoices() +"-"+questions.getAnswer();
		
		return questionData;
	}
	
	@POST
	@Path("mvc/driveQuestions")
	@Produces(MediaType.TEXT_PLAIN)
	public String nextQuestions(@QueryParam("question") String question,
			@QueryParam("answer") String answer,
			@QueryParam("selected") String selectedChoice) {
		
		Questions questions = new Questions();
		
		questions.setQuestion(question);
		questions.setAnswer(answer);
		questions.setOptions(selectedChoice);
		QuestionDriveCommand questionDriveCommand = new QuestionDriveCommand();
		questions = questionDriveCommand.driveQuestions(questions);
		
		String questionData = questions.getQuestion() +"-"+ questions.getChoices() +"-"+questions.getAnswer();
		return questionData;
	}
	
	@POST
	@Path("mvc/addQuestion")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addQuestions(@FormParam("question") String question,
			@FormParam("choices") String choices,
			@FormParam("answer") String answer) {
			
		Questions questions = new Questions();
		
		questions.setQuestion(question);
		//questions.setChoices(choices);
		questions.setAnswer(answer);
		QuestionsMaintanence questionsMaintanence = new QuestionsMaintanence(); 
		questionsMaintanence.addQuestion(questions);;
		}

	@GET
	@Path("mvc/modifyUpdate")
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateModifyQues(){
		QuestionDriveCommand questionDriveCommand = new QuestionDriveCommand();
		List<Questions> questionsList = new ArrayList<Questions>();
		questionsList = questionDriveCommand.modifyUpdate();
		return Response.ok(new Viewable("/modifyQuestions.jsp",questionsList)).build();
	}
	
	@GET
	@Path("mvc/showUsersList")
	@Produces(MediaType.APPLICATION_JSON)
	public String showUsersList(){
		List<userModel> usersList = new ArrayList<userModel>();
		HashMap hashmap = new HashMap();
		
		AdminCommand adminCommand = new AdminCommand();
		usersList = adminCommand.showUsersList();
		hashmap.put("userList", usersList);
		String userString = null;
		try{
			userString = mapper.writeValueAsString(hashmap);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return userString;
	}
	
	@POST
	@Path("mvc/feedback")
	public Response submitFeedback(@FormParam("feedback") String feedback){
		FeedbackCommand feedbackCommand = new FeedbackCommand();
		feedbackCommand.submitFeedback(feedback);
		return Response.ok(new Viewable("/displayQuestions.jsp","")).build();
	}
	
	@GET
	@Path("mvc/goToReg")
	public Response goToReg(){
		return Response.ok(new Viewable("/userRegistration.jsp","")).build();
	}
	
	@GET
	@Path("mvc/addQuestion")
	@Produces(MediaType.TEXT_PLAIN)
	public String addQuestion(@QueryParam("question") String question,
				@QueryParam("options") String options,
				@QueryParam("answer") String answer){
		Questions questions = new Questions();
		questions.setQuestion(question);
		questions.setOptions(options);
		questions.setAnswer(answer);
		AdminCommand adminCommand = new AdminCommand();
		adminCommand.addQuestion(questions);
		String message = "Question Added successfully";
		return message;
	}
	
	@PUT
	@Path("mvc/updateQuestion")
	public Response updateQuestion(@QueryParam("question") String question,
			@QueryParam("choices") String choices,
			@QueryParam("answer") String answer){
		Questions questions = new Questions();
		questions.setQuestion(question);
		questions.setOptions(choices);
		questions.setAnswer(answer);
		AdminCommand adminCommand = new AdminCommand();
		adminCommand.updateQuestion(questions);
		return Response.ok(new Viewable("/admin.jsp","")).build();
	}
	
	@GET
	@Path("mvc/logOut")
	public Response logout(){
		return Response.ok(new Viewable("/userLogin.jsp","")).build();
	}
	
	@DELETE
	@Path("mvc/logoutUser")
	public Response logoutUser(){
		UserValidateCommand userValidateCommand = new UserValidateCommand();
		userValidateCommand.logoutUser();
		return Response.ok(new Viewable("/userLogin.jsp","")).build();
	}
	
	
	@DELETE
	@Path("mvc/deleteUser")
	public Response deleteQuestion(@QueryParam("email") String email){
		userModel usermodel = new userModel(); 
		AdminCommand adminCommand = new AdminCommand();
		usermodel.setUserEmail(email);
		adminCommand.deleteUser(usermodel);
		return Response.ok(new Viewable("/admin.jsp","")).build();
	}
	// Search songs
}
