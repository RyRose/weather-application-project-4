package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class SqlHelperTest {
	
	private final String DATABASE_AUTHORITY = "jdbc:sqlite:sqlhelpertest";
	
	@Test
	public void firstTest() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
	}

	
}
