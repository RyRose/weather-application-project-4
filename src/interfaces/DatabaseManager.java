package interfaces;

import java.util.Date;
import java.util.List;

public interface DatabaseManager {

	public void refreshDatabaseForZipCode(int zip_code);
	
	public List<Day> getDays(int num_days, int zip_code);
	public Day getToday( int zip_code );
	public Day getSpecificDay(Date date, int zip_code);

	boolean networkCheck();

}
