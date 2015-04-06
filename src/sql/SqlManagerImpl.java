package sql;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;


import web.JsonParser;
import interfaces.Day;
import interfaces.Location;
import interfaces.SqlManager;

public class SqlManagerImpl implements SqlManager {
	private SqlHelper helper;
	private JsonParser parser;
	
	public SqlManagerImpl() {
		helper = new SqlHelper("weather-database.db");
	}

	@Override
	public ArrayList<Day> getNumberOfDaysForZipCode(int num_days, int zip_code) {
		refreshDatabaseForZipCode(zip_code);
		
		ArrayList<Day> days = helper.queryDaysForZipCode(zip_code);
		return (ArrayList<Day>) days.subList(0, num_days);
	}

	@Override
	public Day getTodayForZipCode(int zip_code) {
		refreshDatabaseForZipCode(zip_code);
		
		ArrayList<Day> days = helper.queryDaysForZipCode(zip_code);
		return days.get(0);
	}

	@Override
	public Day getSpecificDayForZipCode(Date date, int zip_code) {
		refreshDatabaseForZipCode(zip_code);
		
		ArrayList<Day> days = helper.queryDaysForZipCode(zip_code);
		for (Day day : days) {
			if (day.getDate().equals(date))
				return day;
		}
		
		throw new IllegalArgumentException("Date out of bounds");
	}

	@Override
	public void refreshDatabaseForZipCode(int zip_code){
		if (networkCheck() == false) {return;}
		String zipCodeString = Integer.toString(zip_code);
		
		ArrayList<Location> locations = helper.queryAllLocations();
		
		for ( Location location : locations ) {
			if (location.getZipCode() == zip_code ) {
				try {
					parser = new JsonParser(zipCodeString);
				} catch (IOException e) {
					throw new IllegalArgumentException("Enter a valid zip_code");
				}
				// TODO: pull from api and delete all rows that have the same zip in Weather Table
				//   and then insert all the Days into the Weather Table
				//return;
			}
		}
		try {
			parser = new JsonParser(zipCodeString);
		} catch (IOException e) {
			throw new IllegalArgumentException("Enter a valid zip_code");
		}
		// TODO: Insert zip code into Location Table
		//       and then pull from api to get days and insert all of the Days into the Weather Table
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
	
}
