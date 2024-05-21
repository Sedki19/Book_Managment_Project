/**
 * 
 */
/**
 * 
 */
module Book_Managment_Project {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	
	requires javafx.controls;
	requires java.desktop;
	requires javafx.base;
	exports Application.Main;
	exports Application.Login;
	opens Application.Main to javafx.fxml;
	opens Application.Login to javafx.fxml;
	opens Application.Register to javafx.fxml;
	opens Application.Books to javafx.fxml;
	opens Application.AddBook to javafx.fxml;
	
	opens Book.entities to javafx.base;
}