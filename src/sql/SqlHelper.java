package sql;

import interfaces.Day;
import interfaces.Location;

import java.sql.Connection;
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
	
	public void initializeTables() {
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
		
		final String CREATE_DAY_TABLE = "CREATE TABLE " + Weather.TABLE_NAME + " IF NOT EXISTS " +
				"( " + SqlContract._ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT" +
				", " + SqlContract.COLUMN_ZIP 	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_DATE 		+ " DATE NOT NULL" +
				", " + Weather.COLUMN_HIGH_TEMP + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_LOW_TEMP	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_HUMIDITY	+ " INTEGER NOT NULL" +
				", " + Weather.COLUMN_WIND_SPEED+ " INTEGER NOT NULL" +
				")";

		final String CREATE_LOCATION_TABLE = "CREATE TABLE " + Area.TABLE_NAME + 
				"( " + SqlContract._ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT" +
				", " + SqlContract.COLUMN_ZIP 	+ " INTEGER" +
				", " + Area.COLUMN_CITY 	+ " TEXT)";

		try {
			stat.execute(CREATE_DAY_TABLE);
			stat.execute(CREATE_LOCATION_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Day> queryForZipCode( int zip_code, int num_days ) {
		PreparedStatement queryLocationStatement;
		
		return null;
	}

	
	public void insertIntoDayTable(int zip_code, Day... days) {
		PreparedStatement insertStatement;
		Connection con;
		
		final String insertString = "INSERT INTO " + DATABASE_NAME + "." + Weather.TABLE_NAME + " VALUES (" +
		        "?, ?, ?, ?, ?, ?)";

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			con.setAutoCommit(false);
			insertStatement = con.prepareStatement(insertString);

			for( Day day : days) {
				insertStatement.setInt(1, zip_code);
				insertStatement.setDate(2, day.getDate());
				insertStatement.setDouble(3,  day.getHighTemperature());
				insertStatement.setDouble(4, day.getLowTemperature());
				insertStatement.setDouble(5, day.getWindSpeed());
				insertStatement.setDouble(6, day.getHumidity());
				insertStatement.execute();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void insertIntoLocationTable( Location location ) {
		PreparedStatement insertStatement;
		Connection con;
		
		final String insertString = "INSERT INTO " + DATABASE_NAME + "." + Area.TABLE_NAME + " VALUES (" +
				"?, ?)";
		
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			con.setAutoCommit(false);
			insertStatement = con.prepareStatement(insertString);
			insertStatement.setInt(1, location.getZipCode() );
			insertStatement.setString(2, location.getCityName() );
			insertStatement.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}	
}