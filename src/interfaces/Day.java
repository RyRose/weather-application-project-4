package interfaces;

import java.util.Date;

public interface Day {
	
	public double getCurrent();
	
	public Date getDate();
	public void setDate(long l);  //time in unix format
	
	public double getMin();
	public void setMin(double low_temp);  //lowest temperature
	
	
	public double getMax();
	public void setMax(double high_temp);  //highest temperature
	
	public double getHumidity();
	public void setHumidity(double humidity); //percentage of humidity
	
	public double getSpeed();
	public void setSpeed(double wind_speed);  //wind speed
}
