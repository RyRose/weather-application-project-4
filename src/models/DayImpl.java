package models;


import java.util.Date;
import interfaces.Day;

public class DayImpl implements Day {
	
	Date date;
	double humidity;
	double wind_speed;
	double high_temp;
	double low_temp;
	
	public DayImpl( int unixTime, double humidity, double wind_speed, double high_temp, double low_temp) {
		this.date = new java.util.Date((long)unixTime*1000);
		this.humidity = humidity;
		this.wind_speed = wind_speed;
		this.high_temp = high_temp;
		this.low_temp = low_temp;
	}


	public void setDt(int yawe) {date = new java.util.Date((long)yawe*1000); }
	public void setMin(double temp) {low_temp = temp; }
	public void setMax(double temp) {high_temp  = temp;	}
	public void setHumidity(double currentHumidity) {humidity = currentHumidity;}
	public void setSpeed(double speed) {wind_speed = speed;	}
	
	
	@Override
	public java.util.Date getDt() {return date;}
	@Override
	public double getHumidity() {return humidity;}
	@Override
	public double getMin() {return low_temp;}
	@Override
	public double getMax() {return high_temp;}
	@Override
	public double getSpeed() {return wind_speed;}

	

}
