package interfaces;

import java.util.Date;
import java.util.ArrayList;

public interface SqlManager {

	public ArrayList<Day> getNumberOfDaysForZipCode(int num_days, int zip_code);
	public Day getTodayForZipCode( int zip_code );
	public Day getSpecificDayForZipCode(Date date, int zip_code);
}
