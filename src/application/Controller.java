package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import interfaces.Day;
import interfaces.SqlManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
	@FXML
	private MenuItem oneDay;
	@FXML
	private MenuItem fiveDay;
	@FXML
	private MenuItem sixteenDay;
	@FXML
	private MenuItem refresh;
	@FXML
	private MenuItem quit;
	private ObservableList<Day> days;
	private SqlManager manager;
	private int numDaysToGet;
	private int userZip;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@FXML
	public void initialize() {
		days = FXCollections.observableArrayList();	
		numDaysToGet = 1;
	}
	
	@FXML
	public void add() {
		if (userInput.getText().length() == 0 || userInput.getText() == null) {return;}
		
		userZip = Integer.parseInt(userInput.getText());
		System.out.println(userZip);
		userInput.clear();
		
		if (numDaysToGet == 1) {
			Day newDay = manager.getTodayForZipCode(userZip);
			addToColumns(newDay);
		} else {
			ArrayList<Day> daylist = manager.getNumberOfDaysForZipCode(numDaysToGet, userZip);
			for (Day newDay : daylist) {addToColumns(newDay);}
		}
		
		table.setItems(days);
	}
	
	public void addToColumns (Day newDay) {
		days.add(newDay);
		date.setCellValueFactory(new PropertyValueFactory(df.format(newDay.getDate())));
		temp.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getCurrent())));
		high.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getMax())));
		low.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getMin())));
		humidity.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getHumidity())));
		windSpeed.setCellValueFactory(new PropertyValueFactory(Double.toString(newDay.getSpeed())));
	}
	
	@FXML
	public void SixteenDayForecast() {
		numDaysToGet = 16;
	}
	
	@FXML
	public void FiveDayForecast() {
		numDaysToGet = 5;
	}
	
	@FXML
	public void TodaysForecast() {
		numDaysToGet = 1;
	}
	
	@FXML
	public void refreshLocation() {
		if (String.valueOf(userZip).length() == 0 || String.valueOf(userZip) == null) {return;}
		manager.refreshDatabaseForZipCode(userZip);
	}
	
	@FXML
	public void endApplication(){
		Platform.exit();
	}

	
}
