package application;

import interfaces.Day;
import web.JsonHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
	@FXML
	private Button add;
	@FXML
	private TextArea userInput;
	@FXML
    private TableView<Day> table;
	@FXML
	private TableColumn date;
	@FXML
	private TableColumn temp;
	@FXML
	private TableColumn high;
	@FXML
	private TableColumn low;
	@FXML
	private TableColumn humidity;
	@FXML
	private TableColumn windSpeed;
	private ObservableList<Day> days;
	
	@FXML
	public void initialize() {
		days = FXCollections.observableArrayList();
		/*zipcode.setCellValueFactory(new PropertyValueFactory("zipcode"));
		date.setCellValueFactory(new PropertyValueFactory("date"));
		temp.setCellValueFactory(new PropertyValueFactory("temp"));
		high.setCellValueFactory(new PropertyValueFactory("high"));
		low.setCellValueFactory(new PropertyValueFactory("low"));
		humidity.setCellValueFactory(new PropertyValueFactory("humidity"));
		windTemp.setCellValueFactory(new PropertyValueFactory("windTemp"));
		rainPCT.setCellValueFactory(new PropertyValueFactory("rainPCT"));*/
		
	}
	
	@FXML
	public void add() {
		String userZip = userInput.getText();
		System.out.println(userZip);
		userInput.clear();
		//This is where we will need to take the zipcode from the user and search from
		//the database. Will need to finish when the database is built.
	}

	
}
