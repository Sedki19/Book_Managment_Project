package Application.Login;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import JDBC.Connection.Conn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ApplicationLoginControlor {

	
	 @FXML
	    private Button back;

	    @FXML
	    private Label backT;

	    @FXML
	    private Button login;

	    @FXML
	    private TextField pass;

	    @FXML
	    private TextField user;
	
	@FXML
	private void backFun(ActionEvent e) throws IOException {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Main/Main.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setTitle("Home");
		stage.setScene(new Scene(root));
		stage.show();
		
		//Closing current window
		Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
        currentStage.close();
	}
	
	@FXML
	private void loginFun (ActionEvent e) throws SQLException, IOException {
		PreparedStatement ps =Conn.getcon().prepareStatement("Select * from user where userName = ? and password = ? ");
		ps.setString(1,user.getText());
		ps.setString(2, pass.getText());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("E://fichiers/User.txt"));
			bw.write(rs.getString(1));
			bw.newLine();
			bw.write(rs.getString(2));
			bw.close();
			
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Books/Books.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Books");
			stage.setScene(new Scene(root));
			stage.show();
			
			//Closing current window
			Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
	        currentStage.close();
			
			
		}
		else {
				
			Alert alert = new Alert(AlertType.ERROR);
	 		alert.setTitle("Error");
	 		
	 		alert.setContentText("Invalid Username and Password");
	 		alert.show();
	 		
		}
		
	}
	
}
