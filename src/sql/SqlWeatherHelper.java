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

public class SqlWeatherHelper {
	
	private SqlHelper_2 sqlHelper_2 = new SqlHelper_2();

	public SqlWeatherHelper() {}
	
	public void initializeTables() {
		Connection connection = sqlHelper_2.getConnection();
		
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
			sqlHelper_2.close(connection);
		}
	}
	
	public ArrayList<Day> queryDaysForZipCode( int zip_code, String DB_AUTHORITY ) {
		Connection connection = sqlHelper_2.getConnection();
		ResultSet set;
		ArrayList<Day> days = new ArrayList<Day>();
		
		final String queryJoinString = 
				"SELECT " 	+ Weather.TABLE_NAME + "." + Weather.COLUMN_DATE 		+
				", " 		+ Weather.TABLE_NAME + "." + Weather.COLUMN_HIGH_TEMP 	+
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_LOW_TEMP 	+
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_HUMIDITY 	+ 
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_WIND_SPEED	+
				" FROM " 	+ Weather.TABLE_NAME + ", "+ Area.TABLE_NAME 			+
				" WHERE " 	+ Area.TABLE_NAME 	 + "." + SqlContract.COLUMN_ZIP 	+ " = " + zip_code +
				" AND " 	+ Area.TABLE_NAME	 + "." + SqlContract.COLUMN_ZIP		+ " = " + Weather.TABLE_NAME + "." + SqlContract.COLUMN_ZIP;
				
		try{
			set = connection.createStatement().executeQuery(queryJoinString);
			while (set.next()) {
				
				 System.out.println("Querying db -> date: " + set.getString(1) + "| high: " +
						set.getDouble(2) + "| low: " + set.getDouble(3) + "| humidity: " + set.getDouble(4) + "| wind: " + set.getDouble(5));
				
				Day day = new DayImpl( set.getLong(1), set.getDouble(4), set.getDouble(5), set.getDouble(2), set.getDouble(3));
				days.add(day);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			sqlHelper_2.close(connection);
		}
		
		return days;
	}
	
	public void insertIntoDayTable(int zip_code, String DB_AUTHORITY, ArrayList<Day> days) {
		Connection connection = sqlHelper_2.getConnection();
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
			sqlHelper_2.close(connection);
		}
	}
	
	
	
	
}
