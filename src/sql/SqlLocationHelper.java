package sql;

import interfaces.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sql.SqlContract.Area;
import sql.SqlContract.Weather;

public class SqlLocationHelper extends BaseSqlHelper {
	
	
	
	public SqlLocationHelper(String DB_AUTHORITY){
		super(DB_AUTHORITY);
	};
	
	public void insertLocation( Location location ) {
		Connection connection = getConnection();
		
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
			close(connection);
		}
	}
	
	public boolean containsLocation(Location location) {
		Connection connection = getConnection();
		
		final String queryZipCode = " SELECT * FROM " + Area.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP  + " = " + "?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(queryZipCode);
			stat.setString(1, location.getZipCode());
			return stat.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
		
		return false;
	}
	
	public void deleteWeatherData(Location location) {
		Connection connection = getConnection();
		
		final String queryRemoveString = "DELETE FROM " + Weather.TABLE_NAME + " WHERE " + SqlContract.COLUMN_ZIP + " = " + "?";
		
		try {
			PreparedStatement stat = connection.prepareStatement(queryRemoveString);
			stat.setString(1, location.getZipCode());
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
	
	

}
