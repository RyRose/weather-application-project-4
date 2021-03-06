package application;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import sql.DatabaseManagerImpl;

import interfaces.Day;
import interfaces.DatabaseManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class Controller {
	@FXML
	private Button add;
	@FXML
	private TextArea userInput;
	@FXML
    private TableView<Day> table;

	//Argument added to tables in initialize
	@FXML
	private TableColumn<Day, Date> date;
	@FXML
	private TableColumn<Day, Double> temp;
	@FXML
	private TableColumn<Day, Double> high;
	@FXML
	private TableColumn<Day, Double> low;
	@FXML
	private TableColumn<Day, Double> humidity;
	@FXML
	private TableColumn<Day, Double> windSpeed;
	@FXML
	private TabPane pane;
	@FXML
	private BorderPane object;

	private ObservableList<Day> days;
	private DatabaseManager manager = new DatabaseManagerImpl();
	private int numDaysToGet;
	private String userZip;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private int tabCount = 1;
	
	@FXML
	public void initialize() {
		days = FXCollections.observableArrayList();	
		numDaysToGet = 1;
		table.setPlaceholder(new Label("Enter a zipcode in the textarea above in order to get the weather."));
		pane.getSelectionModel().getSelectedItem().setText("Location " + tabCount);
		
		
		//Names for the PropertyValueFactory are based on the Day class, so fix this if you make changes to it
		date.setCellValueFactory(new PropertyValueFactory<Day, Date>("date"));
		temp.setCellValueFactory(new PropertyValueFactory<Day, Double>("current"));
		high.setCellValueFactory(new PropertyValueFactory<Day, Double>("max"));
		low.setCellValueFactory(new PropertyValueFactory<Day, Double>("min"));
		humidity.setCellValueFactory(new PropertyValueFactory<Day, Double>("humidity"));
		windSpeed.setCellValueFactory(new PropertyValueFactory<Day, Double>("speed"));
		
		table.setItems(days);
		
		//Keylistener for user pressing enter key
		userInput.setOnKeyPressed(event -> {
			KeyCode key = event.getCode();
			if (key == KeyCode.ENTER) {add();}
			//Prevents newlines from appearing in textarea
			event.consume();
		});
	}
	
	@FXML
	public void add() {
		//Makes sure TextArea is not empty
		if (userInput.getText().length() == 0 || userInput.getText() == null) {
			userInput.setPromptText("Please enter a zipcode before pressing the button.");
			return;
		}
		
		clear();
		
		userZip = userInput.getText();
		
		userInput.clear();
		
		//Checks to see which forecast the manager should grab
		List<Day> daylist;
		
		//Adds all the returned days to the days list and checks if network is down
		try {
			daylist = manager.getDays(numDaysToGet, userZip);
			for (Day newDay : daylist) {addToColumns(newDay);}
		} catch (IOException e) {
			userInput.setText("Invalid zip code or network is down.");
		}
		
	}
	
	//Adds a day to the days list so that it is visible on the table
	public void addToColumns (Day newDay) {
		days.add(newDay);
	}
	
	
	//The next three methods are based off of the three APIs that we can pull from:
	//current, five day, and sixteen day forecast. No other options available at this time
	@FXML
	private void SixteenDayForecast() {
		numDaysToGet = 16;
	}
	
	@FXML
	private void FiveDayForecast() {
		numDaysToGet = 5;
	}
	
	@FXML
	private void TodaysForecast() {
		numDaysToGet = 1;
	}
	
	@FXML
	private void refreshLocation() {
		//Checks before hand if userZip is valid or not. Will have to make changes when cities are implemented
		//Will only work if user has put in a zipcode first
		if (String.valueOf(userZip).equals("0") || String.valueOf(userZip).length() == 0) {return;}
		
		try {
			manager.refreshDatabase(userZip);
		} catch (IOException e) {
			userInput.setText("Invalid zip code or internet is down.");
		}
	}
	
	//Ends application
	@FXML
	public void endApplication(){
		Platform.exit();
	}
	
	//Removes all data from the table
	@FXML
	private void clear() {
		days.clear();
		table.setItems(days);
	}
	
	//Method created to grab the borderpane out of an fxmlloader
	@FXML
	private BorderPane getBorderPane() {
		return object;
	}
	
	
	@FXML
	public void addTab() { 
		FXMLLoader loader = new FXMLLoader();
		try {
			//Had to create a new root object and then rip out the BorderPane from it.
			loader.load(this.getClass().getResource("Tab GUI.fxml").openStream());
			Controller temp = loader.getController();
			BorderPane newPane = temp.getBorderPane();
		
			//Create a new tab and give it the ripped BorderPane as its content.
			//CURRENT ISSUE: You can only add a new tab if the very first one is selected. Not sure a way around this.
			Tab newTab = new Tab();
			tabCount += 1;
			newTab.setText("Location " + tabCount);
			newTab.setContent(newPane);
			pane.getTabs().add(newTab);
		} catch (IOException e) {
		 	e.printStackTrace();
		}
	}

	
}
