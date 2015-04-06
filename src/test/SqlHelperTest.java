package test;

import static org.junit.Assert.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import models.LocationImpl;

import org.junit.Test;

import sql.SqlContract.Area;
import sql.SqlContract.Weather;
import sql.SqlHelper;

public class SqlHelperTest {
	
	private final String DATABASE_NAME = "sqlhelpertest.db";
	private final String DATABASE_AUTHORITY = "jdbc:sqlite:" + DATABASE_NAME;
	
	SqlHelper helper = new SqlHelper(DATABASE_NAME);
	
	@Test
	public void firstTest() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		closeTables();
	}
	
	@Test
	public void testInitializing() {
		try {
			assertFalse(getSqlExecutor().executeQuery("SELECT * FROM " + Area.TABLE_NAME).next());
			assertFalse(getSqlExecutor().executeQuery("SELECT * FROM " + Weather.TABLE_NAME).next());
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	@Test
	public void testInsertion() {
		helper.insertIntoLocationTable(new LocationImpl(8675309, null));
		
		try {
			assertTrue(getSqlExecutor().executeQuery("SELECT * FROM " + Area.TABLE_NAME).next());
			assertFalse(getSqlExecutor().executeQuery("SELECT * FROM " + Weather.TABLE_NAME).next());
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
	private Statement getSqlExecutor() {
		Connection con;
		
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DATABASE_AUTHORITY);
			return con.createStatement();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new RuntimeException();
	}
	
	private void closeTables() {
		Connection con;
		Statement stat;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(DATABASE_AUTHORITY);
			stat = con.createStatement();
			stat.execute("DROP TABLE IF EXISTS " + Weather.TABLE_NAME);
			stat.execute("DROP TABLE IF EXISTS " + Area.TABLE_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
