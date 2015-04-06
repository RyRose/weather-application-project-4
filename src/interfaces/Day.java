package interfaces;

public interface Day {

	//public Date getDate();
	//public String getNameOfDay();
	
	public double getCurrent();
	public void setCurrent(double current_temp); //current temp
	
	public java.util.Date getDate();
	public void setDate(int date);  //time in unix format
	
	public double getMin();
	public void setMin(double low_temp);  //lowest temperature
	
	
	public double getMax();
	public void setMax(double high_temp);  //highest temperature
	
	public double getHumidity();
	public void setHumidity(double humidity); //percentage of humidity
	
	public double getSpeed();
	public void setSpeed(double wind_speed);  //wind speed
	//void setDt(java.util.Date currentDate);
	
}
