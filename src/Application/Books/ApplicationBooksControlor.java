package Application.Books;

import Book.entities.CompareRate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import Book.entities.Book;
import JDBC.Connection.Conn;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ApplicationBooksControlor implements Initializable {
	@FXML
	private Button Markbarr;

	@FXML
	private Button addBook;

	@FXML
	private TableColumn<Book, Date> addedDate;

	@FXML
	private TableColumn<Book, String> author;

	@FXML
	private TableView<Book> bookTable;

	@FXML
	private Button deleteBook;

	@FXML
	private ComboBox<String> filterSelect;

	@FXML
	private TableColumn<Book, Integer> id;

	@FXML
	private TableColumn<Book, Integer> rating;

	@FXML
	private CheckBox Accomplished;

	@FXML
	private TextField search;

	@FXML
	private TableColumn<Book, String> status;

	@FXML
	private TableColumn<Book, String> title;

	@FXML
	private CheckBox unAccomplished;

	@FXML
	private Button logout;
	
	@FXML
	private Label userLib;

	ObservableList<Book> bookList = FXCollections.observableArrayList();
	ObservableList<Book> bookListAccomplished = FXCollections.observableArrayList();
	ObservableList<Book> bookListToberead = FXCollections.observableArrayList();
	String userId;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("E://fichiers/User.txt"));
			String line;
			try {
				userId = br.readLine();
			
				userLib.setText(br.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		addedDate.setCellValueFactory(new PropertyValueFactory<Book, Date>("addedDate"));
		rating.setCellValueFactory(new PropertyValueFactory<Book, Integer>("rating"));
		status.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));

		ObservableList<String> fs = FXCollections.observableArrayList();
		fs.add("Date");
		fs.add("Rating");
		filterSelect.setItems(fs);

		try {
			PreparedStatement ps = Conn.getcon().prepareStatement("Select * from book where bUser = ?");
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7));
				if (b.getStatus().equals("Accomplished")) {
					bookListAccomplished.add(b);
				} else {
					bookListToberead.add(b);
				}

				bookList.add(b);
			}
			bookTable.setItems(bookList);

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	
	@FXML
	public void filter (ActionEvent e) {
		if (filterSelect.getValue().equals("Date")) {
			Collections.sort(bookList);
		}
		else {
			Collections.sort(bookList, new CompareRate());
		}
	}
	

	@FXML
	public void AccomplishedFilter(ActionEvent e) {
		if (Accomplished.isSelected() && unAccomplished.isSelected()) {
			bookTable.setItems(bookList);
		} else if (Accomplished.isSelected() && !(unAccomplished.isSelected())) {
			bookTable.setItems(bookListAccomplished);
		} else if (!(Accomplished.isSelected()) && unAccomplished.isSelected()) {
			bookTable.setItems(bookListToberead);
		} else {
			bookTable.setItems(bookList);
		}
	}

	
	public void refrechTables (int id , String ch) {
		bookListAccomplished = FXCollections.observableArrayList();
		bookListToberead = FXCollections.observableArrayList();
		for (int i =0 ; i<bookList.size() ; i++) {
			//update BookList
			if(bookList.get(i).getId() == id) {
				bookList.get(i).setStatus(ch);
			}
			
			//update Other Lists
			if (bookList.get(i).getStatus().equals("Accomplished")) {
				bookListAccomplished.add(bookList.get(i));
			} else {
				bookListToberead.add(bookList.get(i));
			}
		}
	}
	
	
	@FXML
	public void setRead(ActionEvent e) throws SQLException, InterruptedException {
		Book b = bookTable.getSelectionModel().getSelectedItem();
		if (b == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Select a Book first !");
			alert.show();
		} else {
			
			if (b.getStatus().equals("To be read")) {
				Statement stmt = Conn.getcon().createStatement();
				
				if (0 != stmt
						.executeUpdate("update book set status = 'Accomplished' where BookId ='" + b.getId() + "'")) {
					System.out.println("Book updated succesfuly !");
					
					refrechTables(b.getId(),"Accomplished");
					
					
					PauseTransition pause = new PauseTransition(Duration.millis(10)); //Pauser 
					
				    bookTable.setItems(bookListToberead);// it just worked
				    pause.setOnFinished(event -> {
				        
				        System.out.println("Action after pause");
				    
				        bookTable.setItems(bookList);
				    });
				    // Start the pause transition
				    pause.play();
					
					
					
				
				}

			} else {
				Statement stmt = Conn.getcon().createStatement();
				if (0 != stmt
						.executeUpdate("update book set status = 'To be read' where BookId ='" + b.getId() + "'")) {
					System.out.println("Book updated succesfuly !");
				
					
					refrechTables(b.getId(),"To be read");
					
					PauseTransition pause = new PauseTransition(Duration.millis(10)); //Pauser
					
					 bookTable.setItems(bookListAccomplished);// it just worked
					pause.setOnFinished(event -> {
				     
				        System.out.println("Action after pause");
				       
				        bookTable.setItems(bookList);
				        
				    });
				    
					// Start the pause transition
				    pause.play();
				
						
					
		
				
				}
			}
		}

	}
	
	
	@FXML
	public void deleteBook(ActionEvent e) throws SQLException {
				
		
		Book b = bookTable.getSelectionModel().getSelectedItem();
		if (b == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Select a Book first !");
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
	 		alert.setTitle("Delete");
	 		
	 		alert.setContentText("Are you sure you want to delete it ?");
	 		
	 		Optional<ButtonType> result = alert.showAndWait();
	 		if (result.isPresent()) {
	 			if (result.get() == ButtonType.OK) {
	 				
	 				Statement stmt = Conn.getcon().createStatement();
	 				if (0 !=stmt.executeUpdate("delete from book where BookId ='" + b.getId() + "'")) {
	 				bookList.remove(bookTable.getSelectionModel().getSelectedIndex());
	 				}
	 			}
	 		}

		}
		
	}
	
	
	 @FXML
	    void searchFun(KeyEvent event) throws SQLException {
		 
		 
		 bookListAccomplished = FXCollections.observableArrayList();
			bookListToberead = FXCollections.observableArrayList();
			bookList = FXCollections.observableArrayList();
		 Statement stmt = Conn.getcon().createStatement();
		 
			ResultSet rs = stmt.executeQuery("Select * from book where title like'%"+search.getText()+"%' and bUser = '"+userId+"'");
			while (rs.next()) {
				Book b = new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5),
						rs.getString(6), rs.getInt(7));
				if (b.getStatus().equals("Accomplished")) {
					bookListAccomplished.add(b);
				} else {
					bookListToberead.add(b);
				}

				bookList.add(b);
			}
			bookTable.setItems(bookList);

	    }
	 @FXML
	 public void addBookFun(ActionEvent e) throws IOException {
		 
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/AddBook/AddBook.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("AddBook");
			stage.setScene(new Scene(root));
			stage.show();
			
			//Closing current window
			Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
         currentStage.close();
	 }
	 
	 
	 @FXML 
	 public void logOutFun (ActionEvent e) throws IOException {
		 
		 //toBe continued
		 
		 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application/Login/Login.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("HomePage");
			stage.setScene(new Scene(root));
			stage.show();
			
			//Closing current window
			Stage currentStage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
      currentStage.close();
	 }
	
	
}
