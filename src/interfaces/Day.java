package interfaces;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Day {

	//public Date getDate();
	//public String getNameOfDay();
	public double getCurrent();
	public void setCurrent(double temp); //current temp
	
	public java.util.Date getDate();
	public void setDate(int dayDate);  //time in unix format
	
	public double getMin();
	public void setMin(double temp);  //lowest temperature
	
	
	public double getMax();
	public void setMax(double temp);  //highest temperature
	
	public double getHumidity();
	public void setHumidity(double humiditylevel); //percentage of humidity
	
	public double getSpeed();
	public void setSpeed(double windspeed);  //wind speed
	//void setDt(java.util.Date currentDate);
	
}
