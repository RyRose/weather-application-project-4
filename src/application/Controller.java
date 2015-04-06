package application;

import interfaces.Day;
import interfaces.SqlManager;
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
	private SqlManager manager;
	
	@FXML
	public void initialize() {
		days = FXCollections.observableArrayList();	
	}
	
	@FXML
	public void add() {
		int userZip = Integer.parseInt(userInput.getText());
		System.out.println(userZip);
		userInput.clear();
		Day newDay = manager.getTodayForZipCode(userZip);
		//This is where we will need to take the zipcode from the user and search from
		//the database. Will need to finish when the database is built.
		days.add(newDay);
		date.setCellValueFactory(new PropertyValueFactory(newDay.getNameOfDay()));
		temp.setCellValueFactory(new PropertyValueFactory("temp")); //need to implement temp into Day class
		high.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getMax())));
		low.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getMin())));
		humidity.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getHumidity())));
		windSpeed.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getSpeed())));
		
		table.setItems(days);
	}

	
}
