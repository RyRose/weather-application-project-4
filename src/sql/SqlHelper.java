package sql;

import interfaces.Day;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import sql.SqlContract.Weather;
import sql.SqlContract.Location;

public class SqlHelper {

	final static String DATABASE_NAME = "DB";
	final static int VERSION = 1;

	public static void createTables() {
		Connection con;
		Statement stat = null;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:testdb");
			stat = con.createStatement();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		final String CREATE_DAY_TABLE = "CREATE TABLE " + Weather.TABLE_NAME +
				"("  + SqlContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
				", " + SqlContract.COLUMN_ZIP + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_DATE+ " DATE NOT NULL" +
				", " + Weather.COLUMN_HIGH_TEMP + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_LOW_TEMP + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_HUMIDITY + " INTEGER NOT NULL" +
				", " + Weather.COLUMN_WIND_SPEED + " INTEGER NOT NULL" +
				")";

		final String CREATE_LOCATION_TABLE = "CREATE TABLE " + Location.TABLE_NAME + "( " +
				SqlContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				SqlContract.COLUMN_ZIP + " TEXT NOT NULL, " +
				Location.COLUMN_CITY + " TEXT NOT NULL)";

		try {
			stat.execute(CREATE_DAY_TABLE);
			stat.execute(CREATE_LOCATION_TABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertIntoDayTable(int zip_code, Day... days) {
		PreparedStatement insertStatement;
		Connection con;
		
		final String insertString = "INSERT INTO " + DATABASE_NAME + "." + Weather.TABLE_NAME + " VALUES (" +
		        "?, ?, ?, ?, ?, ?)";

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:testdb");
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
}