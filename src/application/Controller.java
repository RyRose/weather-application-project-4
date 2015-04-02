package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;

public class Controller {
	@FXML
	private Button add;
	@FXML
	private TextArea userInput;
	@FXML
	private TableColumn zipcode;
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
	private TableColumn windTemp;
	@FXML
	private TableColumn rainPCT;
	
	Model model = new Model();
	
	@FXML
	private void initialize() {
		zipcode.getColumns().addAll(model.getZipCodes());
		date.getColumns().addAll(model.getDates());
		temp.getColumns().addAll(model.getTemps());
		high.getColumns().addAll(model.getHighs());
		low.getColumns().addAll(model.getLows());
		humidity.getColumns().addAll(model.getHumidity());
		windTemp.getColumns().addAll(model.getWindTemps());
		rainPCT.getColumns().addAll(model.getRainPCTS());
	}
	
	@FXML
	public void add() {
		String userZip = userInput.getText();
		userInput.clear();
		//This is where we will need to take the zipcode from the user and search from
		//the database. Will need to finish when the database is built.
	}

	
}
