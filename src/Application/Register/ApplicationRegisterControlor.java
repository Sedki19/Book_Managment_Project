package Application.Register;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.Connection.Conn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ApplicationRegisterControlor {
	@FXML
    private Button back;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordC;

    @FXML
    private Button register;

    @FXML
    private TextField user;
    
    @FXML
    private Label succmsg;    

    @FXML
    void backFun(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Main/Main.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setTitle("Home");
		stage.setScene(new Scene(root));
		stage.show();
		
		//Closing current window
		Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        
    }
    
    @FXML
    void registerFun(ActionEvent event) throws SQLException, InterruptedException {
    	String vuser = user.getText();
    	String vpassword = password.getText();
    	String vpasswordC = passwordC.getText();
    	
    	//Errors
    	
    	if (vuser.length() <3 || vuser == "") {
    		Alert alert = new Alert(AlertType.ERROR);
	 		alert.setTitle("Error");
	 		
	 		alert.setContentText("Invalid Username ");
	 		alert.show();
	 		return ;
    	}
    	
    	if(vpassword == "" || vpassword.length() <3) {
    		Alert alert = new Alert(AlertType.ERROR);
	 		alert.setTitle("Error");
	 		
	 		alert.setContentText("Invalid Password");
	 		alert.show();
	 		return ;
    	}
    	
    	
    	if (!(vpassword.equals(vpasswordC))) {
    		Alert alert = new Alert(AlertType.ERROR);
	 		alert.setTitle("Error");
	 		
	 		alert.setContentText("Password Confirmation is not correct");
	 		alert.show();
	 		return ;
    	}
    	
    	PreparedStatement ps =Conn.getcon().prepareStatement("Select * from user where userName = ?");
		ps.setString(1,vuser);
		if (ps.executeQuery().next()) {
			Alert alert = new Alert(AlertType.ERROR);
	 		alert.setTitle("Error");
	 		
	 		alert.setContentText("User already exists");
	 		alert.show();
	 		return ;
		}
		
		ps =Conn.getcon().prepareStatement("Insert into user (userName,password) values (?,?)");
		ps.setString(1, vuser);
		ps.setString(2, vpasswordC);
		if (ps.executeUpdate()>0) {
			System.out.print("USER ADDED SUCCESFULY");
			succmsg.setText("USER ADDED SUCCESFULY");
			user.setText("");
			password.setText("");
			passwordC.setText("");
			
		}
		
    }
}
