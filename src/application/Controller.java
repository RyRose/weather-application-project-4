package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
		if (userInput.getText().length() == 0 || userInput.getText() == null) {return;}
		
		int userZip = Integer.parseInt(userInput.getText());
		System.out.println(userZip);
		userInput.clear();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		Day newDay = manager.getTodayForZipCode(userZip);
		days.add(newDay);
		date.setCellValueFactory(new PropertyValueFactory(df.format(newDay.getDate())));
		temp.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getCurrent())));
		high.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getMax())));
		low.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getMin())));
		humidity.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getHumidity())));
		windSpeed.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getSpeed())));
		
		table.setItems(days);
	}

	
}
