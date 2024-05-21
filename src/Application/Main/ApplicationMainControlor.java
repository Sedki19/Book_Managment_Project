package Application.Main;



import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ApplicationMainControlor {

	
	@FXML
    private Button exit;

    @FXML
    private Button login;

    @FXML
    private Button signin;

    
	
	@FXML
	private void exitFun(ActionEvent e) {
		Platform.exit();
	}
	
	@FXML
	private void LoginWinFun(ActionEvent e) throws IOException{
		try {
			
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Login/Login.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Login");
			stage.setScene(new Scene(root));
			stage.show();
			
			//Closing current window
			Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
            currentStage.close();
			
			
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	private void SigninWinFun(ActionEvent e) {
try {
			
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Register/Register.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Login");
			stage.setScene(new Scene(root));
			stage.show();
			
			//Closing current window
			Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
            currentStage.close();
			
			
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
