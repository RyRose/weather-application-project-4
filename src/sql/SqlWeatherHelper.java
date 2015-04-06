package sql;

import interfaces.Day;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.DayImpl;
import sql.SqlContract.Area;
import sql.SqlContract.Weather;

public class SqlWeatherHelper {
	
	private SqlHelper_2 sqlHelper_2 = new SqlHelper_2();

	public SqlWeatherHelper() {}
	
	public void initializeTables() {
		final String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + Area.TABLE_NAME +
				"( " + SqlContract.COLUMN_ZIP 	+ " INTEGER" +
				", " + Area.COLUMN_CITY 		+ " TEXT)";
		
		final String CREATE_DAY_TABLE = "CREATE TABLE IF NOT EXISTS " + Weather.TABLE_NAME +
				"( " + SqlContract.COLUMN_ZIP 	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_DATE 		+ " DATE NOT NULL" +
				", " + Weather.COLUMN_HIGH_TEMP + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_LOW_TEMP	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_HUMIDITY	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_WIND_SPEED+ " INTEGER NOT NULL" +
				")";

		try {
			sqlHelper_2.getExecutor().execute(CREATE_LOCATION_TABLE);
			sqlHelper_2.getExecutor().execute(CREATE_DAY_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Day> queryDaysForZipCode( int zip_code, String DB_AUTHORITY ) {
		Connection con;
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
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			set = con.prepareStatement(queryJoinString).executeQuery();
			
			while (set.next()) {
				Day day = new DayImpl( Date.valueOf(set.getString(0)), set.getDouble(1), set.getDouble(2), set.getDouble(3), set.getDouble(4));
				days.add(day);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return days;
	}
	
	public void insertIntoDayTable(int zip_code, String DB_AUTHORITY, Day... days) {
		PreparedStatement insertStatement;
		Connection con;
		
		final String insertString = "INSERT INTO " + Weather.TABLE_NAME + " VALUES (" +
		        "?, ?, ?, ?, ?, ?)";

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			con.setAutoCommit(false);
			insertStatement = con.prepareStatement(insertString);

			for( Day day : days) {
				insertStatement.setInt(1, zip_code);
				insertStatement.setDate(2, (Date) day.getDate());
				insertStatement.setDouble(3,  day.getMax());
				insertStatement.setDouble(4, day.getMin());
				insertStatement.setDouble(5, day.getSpeed());
				insertStatement.setDouble(6, day.getHumidity());
				insertStatement.executeUpdate();
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	
}
