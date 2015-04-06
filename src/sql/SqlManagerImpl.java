package sql;

import java.util.ArrayList;
import java.util.Date;

import interfaces.Day;
import interfaces.Location;
import interfaces.SqlManager;

public class SqlManagerImpl implements SqlManager {
	
	private SqlHelper helper;
	
	public SqlManagerImpl() {
		helper = new SqlHelper();
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
	public void refreshDatabaseForZipCode(int zip_code) {
		// TODO: Stick a check in for network access that returns 
		
		ArrayList<Location> locations = helper.queryAllLocations();
		
		for ( Location location : locations ) {
			if (location.getZipCode() == zip_code ) {
						// TODO: pull from api and delete all rows that have the same zip in Weather Table
				return;	//       and then insert all the Days into the Weather Table
			}
		}
		
		// TODO: Insert zip code into Location Table
		//       and then pull from api to get days and insert all of the Days into the Weather Table
	}
	
}
