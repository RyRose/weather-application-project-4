package interfaces;

import java.util.Date;

public interface Day {

	public Date getDate();
	public String getNameOfDay();
	
	public double getHighTemperature();
	public double getLowTemperature();
	public double getHumidity();
	public double getWindSpeed();
}
