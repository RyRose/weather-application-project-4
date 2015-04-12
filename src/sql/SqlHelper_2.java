package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  abstract class SqlHelper_2 {
	
	 String DATABASE_NAME;
	 String DB_AUTHORITY;
	
	
	
	public SqlHelper_2(String database_name) {
		DATABASE_NAME = database_name;
		DB_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	

	protected Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_AUTHORITY);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	protected void close( Connection conn ) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
