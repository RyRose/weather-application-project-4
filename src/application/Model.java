package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
	
	private ObservableList<String> zipcodes = FXCollections.observableArrayList();
	private ObservableList<String> dates = FXCollections.observableArrayList();
	private ObservableList<String> temps = FXCollections.observableArrayList();
	private ObservableList<String> highs = FXCollections.observableArrayList();
	private ObservableList<String> lows = FXCollections.observableArrayList();
	private ObservableList<String> humidity = FXCollections.observableArrayList();
	private ObservableList<String> windTemps = FXCollections.observableArrayList();
	private ObservableList<String> rainPCTS = FXCollections.observableArrayList();
	
	ObservableList<String> getZipCodes() {
		return zipcodes;
	}
	
	ObservableList<String> getDates() {
		return dates;
	}
	
	ObservableList<String> getTemps() {
		return temps;
	}
	
	ObservableList<String> getHighs() {
		return highs;
	}
	
	ObservableList<String> getLows() {
		return lows;
	}
	
	ObservableList<String> getHumidity() {
		return humidity;
	}
	
	ObservableList<String> getWindTemps() {
		return windTemps;
	}
	
	ObservableList<String> getRainPCTS() {
		return rainPCTS;
	}
	
	public void add(String zipcode, String date, String temp, String high, String low, String humid, String windTemp, String rainPCT) {
		zipcodes.add(zipcode);
		dates.add(date);
		temps.add(temp);
		highs.add(high);
		lows.add(low);
		humidity.add(humid);
		windTemps.add(windTemp);
		rainPCTS.add(rainPCT);
	}
	
	public void removeAll() {
		zipcodes.clear();
		dates.clear();
		temps.clear();
		highs.clear();
		lows.clear();
		humidity.clear();
		windTemps.clear();
		rainPCTS.clear();
	}
}
