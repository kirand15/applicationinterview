package command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectionprovider.ConnectionProvider;
import model.Questions;
import model.userModel;

public class AdminCommand {
	
	
	ObjectMapper mapper = new ObjectMapper();
	//Logger logger = Logger.getLogger(AdminCommand.class);
	Connection connection;
	
	public AdminCommand(){
		try{
			connection = ConnectionProvider.getConnection();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void addQuestion(Questions questions){
		try{
			String sql = "INSERT INTO questions VALUES(?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, questions.getQuestion());
			preparedStatement.setString(2, questions.getOptions());
			preparedStatement.setString(3, questions.getAnswer());
			preparedStatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void updateQuestion(Questions questions){
		try{
			String sql = "UPDATE questions SET chioces =? AND answer = ? WHERE question = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, questions.getOptions());
			preparedStatement.setString(2, questions.getAnswer());
			preparedStatement.setString(1, questions.getQuestion());
			preparedStatement.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public List<userModel> showUsersList(){
		userModel usermodel;
		List<userModel> usersList = new ArrayList<userModel>();
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM userdata WHERE role ='user'");
			while(rs.next()){
				usermodel = new userModel();
				usermodel.setUserID(rs.getInt("userid"));
				usermodel.setUserFirstName(rs.getString("firstname").trim());
				usermodel.setUserLastNAme(rs.getString("lastname").trim());
				usermodel.setUserEmail(rs.getString("email").trim());
				usermodel.setPhone(rs.getString("phone").trim());
				usersList.add(usermodel);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			//logger.error(ex);
		}
		return usersList;
	}
	
	public void deleteUser(userModel userModel){
		try{
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM userdata WHERE email = '"+userModel.getUserEmail().trim()+"'");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
