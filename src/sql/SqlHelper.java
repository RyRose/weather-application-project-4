package sql;

import interfaces.Day;
import interfaces.Location;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sql.SqlContract.Weather;
import sql.SqlContract.Area;

public class SqlHelper {

	final static String DATABASE_NAME = "DB";
	final static String DB_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
	final static int VERSION = 1;
	
	public SqlHelper() {
		initializeTables();
	}
	
	private void initializeTables() {
		Connection con;
		Statement stat = null;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			stat = con.createStatement();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		final String CREATE_LOCATION_TABLE = "CREATE TABLE " + Area.TABLE_NAME + 
				"( " + SqlContract._ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT" +
				", " + SqlContract.COLUMN_ZIP 	+ " INTEGER" +
				", " + Area.COLUMN_CITY 		+ " TEXT)";
		
		final String CREATE_DAY_TABLE = "CREATE TABLE " + Weather.TABLE_NAME + " IF NOT EXISTS " +
				"( " + SqlContract._ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT" +
				", " + SqlContract.COLUMN_ZIP 	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_DATE 		+ " DATE NOT NULL" +
				", " + Weather.COLUMN_HIGH_TEMP + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_LOW_TEMP	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_HUMIDITY	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_WIND_SPEED+ " INTEGER NOT NULL" +
				")";
		
		try {
			stat.execute(CREATE_LOCATION_TABLE);
			stat.execute(CREATE_DAY_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Day> queryForZipCode( int zip_code ) {
		PreparedStatement queryLocationStatement;
		
		final String queryJoinString = 
				"SELECT " 	+ Weather.TABLE_NAME + "." + Weather.COLUMN_DATE 		+
				", " 		+ Weather.TABLE_NAME + "." + Weather.COLUMN_HIGH_TEMP 	+
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_LOW_TEMP 	+
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_HUMIDITY 	+ 
				", "		+ Weather.TABLE_NAME + "." + Weather.COLUMN_WIND_SPEED	+
				" FROM " 	+ Weather.TABLE_NAME + ", "+ Area.TABLE_NAME 			+
				" WHERE " 	+ Area.TABLE_NAME 	 + "." + SqlContract.COLUMN_ZIP 	+ " = ";
				
		
		return null;
	}

	
	public void insertIntoDayTable(int zip_code, Day... days) {
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
	
	public void insertIntoLocationTable( Location location ) {
		PreparedStatement insertStatement;
		Connection con;
		
		final String insertString = "INSERT INTO " + Area.TABLE_NAME + " VALUES (" +
				"?, ?)";
		
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			con.setAutoCommit(false);
			insertStatement = con.prepareStatement(insertString);
			insertStatement.setInt(1, location.getZipCode() );
			insertStatement.setString(2, location.getCityName() );
			insertStatement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}	
}