package Application.AddBook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import Book.entities.Book;
import JDBC.Connection.Conn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ApplicationBooksControlor implements Initializable {

	 @FXML
	    private Button addBook;

	    @FXML
	    private TextField author;

	    @FXML
	    private Button back;

	    @FXML
	    private ComboBox<Integer> rating;

	    @FXML
	    private ComboBox<String> status;

	    @FXML
	    private TextField title;
	    
	    @FXML
	    private Label addedmsg;
	    
	    String userId;
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	    	
	    	try {
				BufferedReader br = new BufferedReader(new FileReader("E://fichiers/User.txt"));
				String line;
				try {
					userId = br.readLine();
					System.out.print(userId);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	ObservableList<Integer> rateList = FXCollections.observableArrayList();
	    	for (int i = 0 ; i<=5 ;i++) {
	    		rateList.add(i);
	    	}
			rating.setItems(rateList);
			
			
			ObservableList<String> statList = FXCollections.observableArrayList();
			statList.add("Accomplished");
			statList.add("To be read");
			
			status.setItems(statList);
		}
	    
	    
	    @FXML
	    public void backFun(ActionEvent e) throws IOException {
	    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Books/Books.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Book");
			stage.setScene(new Scene(root));
			stage.show();
			
			//Closing current window
			Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
         currentStage.close();
	    }
	    
	    @FXML
	    public void addBookFun(ActionEvent e) throws SQLException {
	    	if (title.getText().equals("")){
	    		Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Fill title field !");
				alert.show();
				return;
	    	}
	    	if (author.getText().equals("")) {
	    		Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Fill author field !");
				alert.show();
				return;
	    	}
	    	if (rating.getValue() == null) {
	    		Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Select rate for the book !");
				alert.show();
				return;
	    	}
	    	if (status.getValue() == null) {
	    		Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Select the book status !");
				alert.show();
				return;
	    	}
	    	Date today = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedDate = formatter.format(today);
	    	PreparedStatement ps = Conn.getcon().prepareStatement("Insert into book(title,author,addedDate,rating,status,bUser) values (?,?,"+"'"+formattedDate+"'"+",?,?,?)");
			ps.setString(1,title.getText());
			ps.setString(2,author.getText());
			ps.setInt(3, rating.getValue());
			ps.setString(4, status.getValue());
			ps.setString(5, userId);
			
	    	if(0!= ps.executeUpdate()) {
	    		addedmsg.setText("Book added succesfuly");
				System.out.println("Book added succesfuly");
				author.setText("");
				title.setText("");
				rating.setValue(null);
				status.setValue(null);
			}
	    	else {
	    		System.out.print("dra chsar");
	    	}
			
			
	    }


		
	    
	    
}
