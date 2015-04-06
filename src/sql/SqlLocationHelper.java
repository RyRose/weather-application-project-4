package sql;

import interfaces.Location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.LocationImpl;
import sql.SqlContract.Area;

public class SqlLocationHelper {
	
	public SqlLocationHelper(){};
	
	public ArrayList<Location> queryAllLocations( String DB_AUTHORITY) {
		Connection con;
		ResultSet set;
		ArrayList<Location> locations = new ArrayList<Location>();
		
		final String queryAllLocations = 
				"SELECT * FROM " + Area.TABLE_NAME;
		
		try{
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			set = con.prepareStatement(queryAllLocations).executeQuery();
			while (set.next()) {
				Location location = new LocationImpl(set.getInt(0), set.getString(1));
				locations.add(location);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return locations;
	} 
	public void insertIntoLocationTable( Location location, String DB_AUTHORITY ) {
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
			con.commit();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void zipCodeInLocationTable(int zip_code) {
		final String queryZip_code = " SELECT * FROM " + Area.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP  + " IN " + (zip_code);
				
	}
}
