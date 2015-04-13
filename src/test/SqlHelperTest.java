package test;

import static org.junit.Assert.*;
import interfaces.Day;
import interfaces.Location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.DayImpl;
import models.LocationImpl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import sql.SqlContract;
import sql.SqlContract.Area;
import sql.SqlContract.Weather;
import sql.SqlLocationHelper;
import sql.SqlWeatherHelper;

public class SqlHelperTest {
	
	private final String DATABASE_NAME = "test.db";
	private final String DATABASE_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
	private int TEST_NUM = 5;
	
	SqlLocationHelper locationHelper;
	SqlWeatherHelper weatherHelper;
	Connection connection;
	
	@Before
	public void Before() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		locationHelper = new SqlLocationHelper(DATABASE_NAME);
		weatherHelper = new SqlWeatherHelper(DATABASE_NAME);
		connection = getConnection();
	}
	
	@After
	public void After() throws SQLException {
		connection.close();
		
		connection = getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS " + SqlContract.Area.TABLE_NAME);
		statement.execute("DROP TABLE IF EXISTS " + SqlContract.Weather.TABLE_NAME);
		connection.close();
	}
	
	@Test
	public void testInitializing() {
		try {
			assertFalse(getExecutor().executeQuery("SELECT * FROM " + Area.TABLE_NAME).next());
			assertFalse(getExecutor().executeQuery("SELECT * FROM " + Weather.TABLE_NAME).next());
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	@Test
	public void testLocationInsertion() throws SQLException {
		Location location = getLocations(1).get(0);
		locationHelper.insertLocation(location);
		
		ResultSet rs = getExecutor().executeQuery("SELECT * FROM " + Area.TABLE_NAME);
		
		rs.next();
		
		assertTrue(location.equals(convertToLocation(rs)));
	}
	
	@Test
	public void testDayInsertion() throws SQLException { // TODO
		ArrayList<Day> days = getDays(TEST_NUM);
		
		weatherHelper.insertDays( getLocations(1).get(0) , days);
		
		ResultSet set = getExecutor().executeQuery("SELECT * FROM " + Weather.TABLE_NAME);
		
		int i = 0;
		
		for(; set.next(); i++) {
			Day day = convertToDay(set);
			assertTrue(days.get(i).equals(day));
		}
		
		assertEquals(i, TEST_NUM);
	}
	
	@Test
	public void testLocationInDatabase() {
		ArrayList<Location> locations = getLocations(TEST_NUM);
		
		for( Location location : locations ) {
			assertFalse(locationHelper.containsLocation(location));
			locationHelper.insertLocation(location);
			assertTrue(locationHelper.containsLocation(location));
		}
	}
	
	@Test
	public void testDayInDatabase() {
		ArrayList<Day> days = getDays(TEST_NUM);
		
		for( Day day : days )
			assertFalse(weatherHelper.containsDay(day));
		
		weatherHelper.insertDays( getLocations(1).get(0) , days);
		
		for (Day day : days )
			assertTrue(weatherHelper.containsDay(day));
	}
	
	@Test
	public void testDeleteWeather() throws SQLException {
		Location location = getLocations(1).get(0);
		ArrayList<Day> days = getDays(TEST_NUM);
		
		locationHelper.insertLocation(location);
		
		weatherHelper.insertDays(location, days);
		
		for( Day day : days) {
			assertTrue(weatherHelper.containsDay(day));
		}
		
		locationHelper.deleteWeatherData(location);
		
		for ( Day day : days ) {
			assertFalse( weatherHelper.containsDay(day) );
		}
	}

	private Day convertToDay( ResultSet set ) throws SQLException {
		Day day = new DayImpl();
		day.setDate(set.getLong(Weather.COLUMN_DATE));
		day.setHumidity(set.getDouble(Weather.COLUMN_HUMIDITY));
		day.setMax(set.getDouble(Weather.COLUMN_HIGH_TEMP));
		day.setMin(set.getDouble(Weather.COLUMN_LOW_TEMP));
		day.setSpeed(set.getDouble(Weather.COLUMN_WIND_SPEED));
		return day;
	}
	
	private Location convertToLocation( ResultSet set ) throws SQLException {
		Location location = new LocationImpl();
		location.setZipCode(set.getString(SqlContract.COLUMN_ZIP));
		location.setCityName(set.getString(Area.COLUMN_CITY));
		
		return location;
	}
	
	private ArrayList<Location> getLocations( int num_locations ) {
		ArrayList<Location> list = new ArrayList<>();
		
		for (int i = 0; i < num_locations; i++)
			list.add( new LocationImpl(String.valueOf(i), String.valueOf(i + 1)) );
		
		return list;
	}
	
	private ArrayList<Day> getDays( int num_days ) {
		ArrayList<Day> list = new ArrayList<Day>();
		
		for (int i = 0; i < num_days; i++)
			list.add( new DayImpl(Long.valueOf(i), i+1, i+2, i+3, i+4) );
		
		return list;
	}
	
	private Connection getConnection() {
		try {
			return DriverManager.getConnection(DATABASE_AUTHORITY);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	private Statement getExecutor() {
		try {
			return connection.createStatement();
		} catch (SQLException e) {}
		
		return null;
	}
}
