package sql;

import interfaces.Day;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper_2 {
	
	final static String DATABASE_NAME = "DB";
	final static String DB_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
	final static int VERSION = 1;
	
	private SqlWeatherHelper weatherHelper = new SqlWeatherHelper();
	private SqlLocationHelper locationHelper = new SqlLocationHelper();
	
	public SqlHelper_2() {
		initializeTables();
	}
	
	public void initializeTables(){
		weatherHelper.initializeTables();
	}
	
	public void queryDaysForZipCode( int zip_code ){
		weatherHelper.queryDaysForZipCode(zip_code, DB_AUTHORITY );
	}
	
	public void queryAllLocations(){
		locationHelper.queryAllLocations(DB_AUTHORITY);
	}
	
	public void insertIntoDayTable(int zip_code, Day...days){
		weatherHelper.insertIntoDayTable(zip_code, DB_AUTHORITY, days);
	}
	
	public void insertIntoLocationTable(){
		
		locationHelper.insertIntoLocationTable(null, DB_AUTHORITY); // Location Array needs to be passed
	}
	
	public void zipCodeInLocationTable(int zip_code){
		
		locationHelper.zipCodeInLocationTable(zip_code);
	}
	
	public void deleteWeatherDataForZipCode(int zip_code, String DB_AUTHORITY){
		locationHelper.deleteWeatherDataForZipCode(zip_code, DB_AUTHORITY);
	}
	
	public Statement getExecutor() {
		Statement stat;
		Connection con;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DB_AUTHORITY);
			stat = con.createStatement();
			return stat;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
}
