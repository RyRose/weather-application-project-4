package sql;

import interfaces.Day;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.DayImpl;
import sql.SqlContract.Area;
import sql.SqlContract.Weather;

public class SqlWeatherHelper extends SqlHelper_2 {
	

	public SqlWeatherHelper(String DB_AUTHORITY) {
		super(DB_AUTHORITY);
		initializeTables();
	}
	
	private void initializeTables() {
		Connection connection = getConnection();
		
		final String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + Area.TABLE_NAME +
				"( " + SqlContract.COLUMN_ZIP 	+ " TEXT" +
				", " + Area.COLUMN_CITY 		+ " TEXT)";
		
		final String CREATE_DAY_TABLE = "CREATE TABLE IF NOT EXISTS " + Weather.TABLE_NAME +
				"( " + SqlContract.COLUMN_ZIP 	+ " TEXT NOT NULL" +
				", " + Weather.COLUMN_DATE 		+ " BIGINT NOT NULL" +
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
	
	public ArrayList<Day> getDays( String zip_code ) {
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
			stat.setString(1, zip_code);
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
	
	public void insertDays(String zip_code, ArrayList<Day> days) {
		Connection connection = getConnection();
		PreparedStatement insertStatement;
		
		final String insertString = "INSERT INTO " + Weather.TABLE_NAME + " VALUES (" +
		        "?, ?, ?, ?, ?, ?)";

		try {
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertString);

			for( Day day : days) {
				insertStatement.setString(1, zip_code);
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
	
	public boolean containsDay(Day day) {
		Connection connection = getConnection();
		
		final String queryZipCode = " SELECT * FROM " + Weather.TABLE_NAME + " WHERE " + Weather.COLUMN_DATE  + "=" + "?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(queryZipCode);
			stat.setLong(1, day.getDate().getTime());
			return stat.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
		
		return false;
	}
	}
	
