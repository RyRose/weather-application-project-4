package sql;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.LocationImpl;


import web.ZipcodeData;
import interfaces.Day;
import interfaces.Location;
import interfaces.SqlManager;

/*public class SqlManagerImpl implements SqlManager {
	

	String DB_AUTHORITY = "jdbc:sqlite:weather.db";
	
	private SqlWeatherHelper weatherHelper = new SqlWeatherHelper(DB_AUTHORITY);
	private SqlLocationHelper locationHelper = new SqlLocationHelper(DB_AUTHORITY);

	
	
	public SqlManagerImpl() {}

	@Override
	public List<Day> getNumberOfDaysForZipCode(int num_days, int zip_code) {
		refreshDatabaseForZipCode(zip_code);
		
		ArrayList<Day> days = weatherHelper.queryDaysForZipCode(zip_code);
		return days.subList(0, num_days);
	}

	@Override
	public Day getTodayForZipCode(int zip_code) {
		refreshDatabaseForZipCode(zip_code);
		
		ArrayList<Day> days = weatherHelper.queryDaysForZipCode(zip_code);
		return days.get(0);
	}

	@Override
	public Day getSpecificDayForZipCode(Date date, int zip_code) {
		refreshDatabaseForZipCode(zip_code);
		
		ArrayList<Day> days = weatherHelper.queryDaysForZipCode(zip_code);
		for (Day day : days) {
			if (day.getDate().equals(date))
				return day;
		}
		
		throw new IllegalArgumentException("Date out of bounds");
	}

	@Override
	public void refreshDatabaseForZipCode(int zip_code){
		if (networkCheck() == false) 
			return;
		
		ArrayList<Day> days = null;
		
		for ( Location location : locationHelper.queryAllLocations() ) {
			if (location.getZipCode() == zip_code ) {
				try {
					days = ZipcodeData.getDays(Integer.toString(zip_code), 16);
				} catch (IOException e) {
					e.printStackTrace();
				}
				locationHelper.deleteWeatherDataForZipCode(zip_code);
				weatherHelper.insertIntoDayTable(zip_code, days);
				return;
			}
		}
		
		locationHelper.insertIntoLocationTable( new LocationImpl(zip_code, null));
		
		try {
			days = ZipcodeData.getDays(Integer.toString(zip_code), 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		weatherHelper.insertIntoDayTable(zip_code, days);
	}
	
	@Override
	public boolean networkCheck() {
		try {
			final URL url = new URL("http://openweathermap.org/");
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}
	
}*/
