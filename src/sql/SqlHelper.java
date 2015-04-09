package sql;

import interfaces.Day;
import interfaces.Location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.DayImpl;
import models.LocationImpl;

import sql.SqlContract.Weather;
import sql.SqlContract.Area;

public class SqlHelper {

	String DATABASE_NAME;
	String DB_AUTHORITY;
	
	
	public SqlHelper(String database_name) {
		DATABASE_NAME = database_name;
		DB_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		initializeTables();
	}
	
	private void initializeTables() {
		Connection connection = getConnection();
		
		final String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + Area.TABLE_NAME +
				"( " + SqlContract.COLUMN_ZIP 	+ " INTEGER" +
				", " + Area.COLUMN_CITY 		+ " TEXT)";
		
		final String CREATE_DAY_TABLE = "CREATE TABLE IF NOT EXISTS " + Weather.TABLE_NAME +
				"( " + SqlContract.COLUMN_ZIP 	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_DATE 		+ " TEXT NOT NULL" +
				", " + Weather.COLUMN_HIGH_TEMP + " REAL NOT NULL" +
				", " + Weather.COLUMN_LOW_TEMP	+ " REAL NOT NULL" +
				", " + Weather.COLUMN_HUMIDITY	+ " REAL NOT NULL" +
				", " + Weather.COLUMN_WIND_SPEED+ " REAL NOT NULL" +
				")";

		try {
			Statement statement = connection.createStatement();
			statement.execute(CREATE_LOCATION_TABLE);
			statement.execute(CREATE_DAY_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
	
	public ArrayList<Day> queryDaysForZipCode( int zip_code ) {
		Connection connection = getConnection();
		ResultSet set;
		ArrayList<Day> days = new ArrayList<Day>();
		
		final String queryJoinString = 
				"SELECT " 	+ Weather.TABLE_NAME + "." + Weather.COLUMN_DATE 		+
				", " 		+ Weather.TABLE_NAME + "." + Weather.COLUMN_HIGH_TEMP 	+
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_LOW_TEMP 	+
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_HUMIDITY 	+ 
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_WIND_SPEED	+
				" FROM " 	+ Weather.TABLE_NAME + ", "+ Area.TABLE_NAME 			+
				" WHERE " 	+ Area.TABLE_NAME 	 + "." + SqlContract.COLUMN_ZIP 	+ " = " + "?" +
				" AND " 	+ Area.TABLE_NAME	 + "." + SqlContract.COLUMN_ZIP		+ " = " + Weather.TABLE_NAME + "." + SqlContract.COLUMN_ZIP;
				
		try{
			PreparedStatement stat = connection.prepareStatement(queryJoinString);
			stat.setInt(1, zip_code);
			
			set = stat.executeQuery();
			while (set.next()) {				
				Day day = new DayImpl( set.getLong(1), set.getDouble(4), set.getDouble(5), set.getDouble(2), set.getDouble(3));
				days.add(day);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(connection);
		}
		
		return days;
	}
	
	public ArrayList<Location> queryAllLocations() {
		Connection connection = getConnection();
		ResultSet set;
		ArrayList<Location> locations = new ArrayList<Location>();
		
		final String queryAllLocations = 
				"SELECT * FROM " + Area.TABLE_NAME;
		
		try{
			set = connection.createStatement().executeQuery(queryAllLocations);
			while (set.next()) {
				Location location = new LocationImpl(set.getInt(1), set.getString(2));
				locations.add(location);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(connection);
		}
		
		return locations;
	} 

	
	public void insertIntoDayTable(int zip_code, ArrayList<Day> days) {
		Connection connection = getConnection();
		PreparedStatement insertStatement;
		
		final String insertString = "INSERT INTO " + Weather.TABLE_NAME + " VALUES (" +
		        "?, ?, ?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertString);

			for( Day day : days) {
				insertStatement.setInt(1, zip_code);
				insertStatement.setLong(2, day.getDate().getTime() );
				insertStatement.setDouble(3,  day.getMax());
				insertStatement.setDouble(4, day.getMin());
				insertStatement.setDouble(5, day.getHumidity());
				insertStatement.setDouble(6, day.getSpeed());
				insertStatement.executeUpdate();
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(connection);
		}
	}
	
	public void insertIntoLocationTable( Location location ) {
		Connection connection = getConnection();
		
		PreparedStatement insertStatement;
		
		final String insertString = "INSERT INTO " + Area.TABLE_NAME + " VALUES (" +
				"?, ?)";
		
		try {
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertString);
			insertStatement.setInt(1, location.getZipCode() );
			insertStatement.setString(2, location.getCityName() );
			insertStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(connection);
		}
	}
	
	public boolean zipCodeInLocationTable(int zip_code) {
		Connection connection = getConnection();
		
		final String queryZipCode = " SELECT * FROM " + Area.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP  + " IN (" + "?" + ")";
		
		try {
			PreparedStatement stat = connection.prepareStatement(queryZipCode);
			stat.setInt(1, zip_code);
			return stat.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
		
		return false;
	}
	
	public void deleteWeatherDataForZipCode(int zip_code) {
		Connection connection = getConnection();
		
		final String queryRemoveString = "DELETE FROM " + Weather.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP + " = " + "?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(queryRemoveString);
			stat.setInt(1, zip_code);
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
	
	private Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_AUTHORITY);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	private void close( Connection conn ) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}