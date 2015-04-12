package interfaces;

import java.io.IOException;
import java.util.List;

public interface DatabaseManager {

	public void refreshDatabase(String zip_code) throws IOException;
	public List<Day> getDays(int num_days, String zip_code) throws IOException;
}
