package sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.LocationImpl;


import web.ZipcodeData;
import interfaces.Day;
import interfaces.DatabaseManager;

public class DatabaseManagerImpl implements DatabaseManager {
	
	private SqlWeatherHelper weatherHelper;
	private SqlLocationHelper locationHelper;
	
	public DatabaseManagerImpl() {
		 weatherHelper = new SqlWeatherHelper("weather.db");
		 locationHelper = new SqlLocationHelper("weather.db");
	}

	@Override
	public List<Day> getDays(int num_days, String zip_code) throws IOException {
		refreshDatabaseForZipCode(zip_code);
		ArrayList<Day> days = weatherHelper.getDays(zip_code);
		return days.subList(0, num_days);
	}

	@Override
	public void refreshDatabaseForZipCode(String zip_code) throws IOException {
		ArrayList<Day> days = null;
		
		days = ZipcodeData.getDays(zip_code, 16);
		
		if ( !locationHelper.containsLocation( new LocationImpl(zip_code, null) ) ) // If the location does not exist in database, the location is added to database
			locationHelper.insertLocation( new LocationImpl(zip_code, null) );
		else // else, it deletes the old, out-of-date data
			locationHelper.deleteWeatherData(zip_code);
		
		weatherHelper.insertDays(zip_code, days);	
	}	
}
