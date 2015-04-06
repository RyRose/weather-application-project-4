package application;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import models.DayImpl;
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
	@FXML
	private MenuItem test;
	private ObservableList<Day> days;
	private SqlManager manager;
	private int numDaysToGet;
	private int userZip;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@FXML
	public void initialize() {
		days = FXCollections.observableArrayList();	
		numDaysToGet = 1;
		date.setCellValueFactory(new PropertyValueFactory<Day, Date>("date"));
		temp.setCellValueFactory(new PropertyValueFactory<Day, Double>("current"));
		high.setCellValueFactory(new PropertyValueFactory<Day, Double>("max"));
		low.setCellValueFactory(new PropertyValueFactory<Day, Double>("min"));
		humidity.setCellValueFactory(new PropertyValueFactory<Day, Double>("humidity"));
		windSpeed.setCellValueFactory(new PropertyValueFactory<Day, Double>("speed"));
		table.setItems(days);
	}
	
	@FXML
	public void add() {
		if (userInput.getText().length() == 0 || userInput.getText() == null) {
			userInput.setPromptText("Please enter a zipcode before pressing the button.");
			return;
		}
		
		userZip = Integer.parseInt(userInput.getText());
		System.out.println(userZip);
		userInput.clear();
		userInput.setPromptText("Enter zip code here.");
		
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
		System.out.println(days);
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
		if (String.valueOf(userZip).equals("0") || String.valueOf(userZip).length() == 0) {return;}
		manager.refreshDatabaseForZipCode(userZip);
	}
	
	@FXML
	public void endApplication(){
		Platform.exit();
	}
	
	@FXML
	public void testAdding() {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		Day newDay = new DayImpl(sqlDate, 2.0, 3.0, 4.0, 5.0);
		System.out.println(newDay.getCurrent());
		System.out.println(newDay.getMax());
		System.out.println(newDay.getMin());
		System.out.println(newDay.getHumidity());
		System.out.println(newDay.getSpeed());
		
		addToColumns(newDay);
		table.setItems(days);
	}

	
}
