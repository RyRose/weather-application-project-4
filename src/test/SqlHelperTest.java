package test;

import static org.junit.Assert.*;


import interfaces.Day;

import java.sql.Connection;
import java.sql.DriverManager;
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
import sql.SqlHelper;

public class SqlHelperTest {
	
	private final String DATABASE_NAME = "test.db";
	private final String DATABASE_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
	
	SqlHelper helper;
	Connection connection;
	
	@Before
	public void Before() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		helper = new SqlHelper(DATABASE_NAME);
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
	public void testLocationInsertion() {
		helper.insertLocation(new LocationImpl("8675309", null));
		try {
			assertTrue(getExecutor().executeQuery("SELECT * FROM " + Area.TABLE_NAME).next());
			assertFalse(getExecutor().executeQuery("SELECT * FROM " + Weather.TABLE_NAME).next());
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	@Test
	public void testDayInsertion() { // TODO
		helper.insertDays("00000", getDays(100));
	}
	
	@Test
	public void testZipCodeInTable() { // TODO
		
	}
	
	@Test
	public void testDeleteWeather() { // TODO
		
	}
	
	@Test
	public void testQueryingLocations() { // TODO
		
	}
	
	@Test
	public void testQueryingDays() { // TODO
		
	}
	
	private ArrayList<Day> getDays( int num_days ) {
		ArrayList<Day> list = new ArrayList<Day>();
		
		for (int i = 0; i < num_days; i++)
			list.add( new DayImpl() );
		
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
