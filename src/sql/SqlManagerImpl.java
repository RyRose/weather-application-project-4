package sql;

import java.util.ArrayList;
import java.util.Date;

import interfaces.Day;
import interfaces.SqlManager;

public class SqlManagerImpl implements SqlManager {
	
	private SqlHelper helper;
	
	public SqlManagerImpl() {
		helper = new SqlHelper();
		helper.initializeTables();
	}

	@Override
	public ArrayList<Day> getNumberOfDaysForZipCode(int num_days, int zip_code) {
		return null;
	}

	@Override
	public Day getTodayForZipCode(int zip_code) {
		return null;
	}

	@Override
	public Day getSpecificDayForZipCode(Date date, int zip_code) {
		return null;
	}

	
}
