package sql;

import interfaces.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.LocationImpl;
import sql.SqlContract.Area;
import sql.SqlContract.Weather;

public class SqlLocationHelper {
	
	private SqlHelper_2 sqlHelper_2 = new SqlHelper_2("weather6-db.db");
	
	public SqlLocationHelper(){};
	
	public ArrayList<Location> queryAllLocations( String DB_AUTHORITY ) {
		Connection connection = sqlHelper_2.getConnection();
		ResultSet set;
		ArrayList<Location> locations = new ArrayList<Location>();
		
		final String queryAllLocations = 
				"SELECT * FROM " + Area.TABLE_NAME;
		
		try{
			set = connection.createStatement().executeQuery(queryAllLocations);
			while (set.next()) {
				Location location = new LocationImpl(set.getString(1), set.getString(2));
				locations.add(location);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			sqlHelper_2.close(connection);
		}
		
		return locations;
	} 
	public void insertIntoLocationTable( Location location, String DB_AUTHORITY ) {
		Connection connection = sqlHelper_2.getConnection();
		
		PreparedStatement insertStatement;
		
		final String insertString = "INSERT INTO " + Area.TABLE_NAME + " VALUES (" +
				"?, ?)";
		
		try {
			connection.setAutoCommit(false);
			insertStatement = connection.prepareStatement(insertString);
			insertStatement.setString(1, location.getZipCode() );
			insertStatement.setString(2, location.getCityName() );
			insertStatement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			sqlHelper_2.close(connection);
		}
	}
	
	public boolean zipCodeInLocationTable(int zip_code) { // TODO: convert to PreparedStatement to prevent SQL injection		
		Connection connection = sqlHelper_2.getConnection();
		
		final String queryZip_code = " SELECT * FROM " + Area.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP  + " IN (" + zip_code + ")";
		
		try {
			return connection.createStatement().executeQuery(queryZip_code).next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlHelper_2.close(connection);
		}
		
		return false;
	}
	
	public void deleteWeatherDataForZipCode(int zip_code, String DB_AUTHORITY) { // TODO: convert to PreparedStatement to prevent SQL injection
		Connection connection = sqlHelper_2.getConnection();
		
		final String queryRemoveString = "DELETE FROM " + Weather.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP + " = " + zip_code;
		
		try {
			connection.createStatement().execute(queryRemoveString);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlHelper_2.close(connection);
		}
	}
	
	

}
