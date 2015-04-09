package interfaces;

import java.util.Date;
import java.util.List;

public interface SqlManager {

	public void refreshDatabaseForZipCode(int zip_code);
	
	public List<Day> getNumberOfDaysForZipCode(int num_days, int zip_code);
	public Day getTodayForZipCode( int zip_code );
	public Day getSpecificDayForZipCode(Date date, int zip_code);

	boolean networkCheck();

}
