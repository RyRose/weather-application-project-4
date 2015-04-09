package application;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import sql.DatabaseManagerImpl;

import models.DayImpl;
import interfaces.Day;
import interfaces.DatabaseManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	//Argument added to tables in initialize
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
	private DatabaseManager manager = new DatabaseManagerImpl();
	private int numDaysToGet;
	private int userZip;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@FXML
	public void initialize() {
		days = FXCollections.observableArrayList();	
		numDaysToGet = 1;
		table.setPlaceholder(new Label("Enter a zipcode in the textarea above in order to get the weather."));
		
		//Names for the PropertyValueFactory are based on the Day class, so fix this if you make changes to it
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
		//Makes sure TextArea is not empty
		if (userInput.getText().length() == 0 || userInput.getText() == null) {
			userInput.setPromptText("Please enter a zipcode before pressing the button.");
			return;
		}
		
		userZip = Integer.parseInt(userInput.getText());
		userInput.clear();
		userInput.setPromptText("Enter zip code here.");
		
		//Checks to see which forecast the manager should grab
		if (numDaysToGet == 1) {
			Day newDay = manager.getToday(userZip);
			addToColumns(newDay);
		} else {
			List<Day> daylist = manager.getDays(numDaysToGet, userZip);
			for (Day newDay : daylist) {addToColumns(newDay);}
		}
	}
	
	public void addToColumns (Day newDay) {
		days.add(newDay);
	}
	
	
	//The next three methods are based off of the three APIs that we can pull from:
	//current, five day, and sixteen day forecast. No other options available at this time
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
		//Checks before hand if userZip is valid or not. Will have to make changes when cities are implemented
		//Will only work if user has put in a zipcode first
		if (String.valueOf(userZip).equals("0") || String.valueOf(userZip).length() == 0) {return;}
		manager.refreshDatabaseForZipCode(userZip);
	}
	
	@FXML
	public void endApplication(){
		Platform.exit();
	}
	
	@FXML
	public void testAdding() {
		//Creates sql.Date object based off of util.Date object
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		//Info based on DC weather at 12:44 on 4/6/15
		Day newDay = new DayImpl(sqlDate.getTime(), 40.0, 5.56, 21.9, 16.3);
		
		addToColumns(newDay);
	}
	
	@FXML
	public void clear() {
		days.clear();
		table.setItems(days);
	}

	
}
