package sql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.LocationImpl;
import web.ZipcodeData;
import interfaces.Day;
import interfaces.DatabaseManager;
import interfaces.Location;

public class DatabaseManagerImpl implements DatabaseManager {
	private SqlHelper helper;
	
	public DatabaseManagerImpl() {
		helper = new SqlHelper("weather.db");
	}

	@Override
	public List<Day> getDays(int num_days, String zip_code) throws IOException {
		refreshDatabase(zip_code);
		ArrayList<Day> days = helper.getDays( new LocationImpl(zip_code, null) );
		return days.subList(0, num_days);
	}

	@Override
	public void refreshDatabase(String zip_code) throws IOException {
		ArrayList<Day> days = null;
		Location location = new LocationImpl(zip_code, null);
		
		days = ZipcodeData.getDays(zip_code, 16);
		
		if ( !helper.containsLocation( location ) ) // If the location does not exist in database, the location is added to database
			helper.insertLocation( location );
		else // else, it deletes the old, out-of-date data
			helper.deleteWeatherData(location);
		
		helper.insertDays(location, days);	
	}	
}
