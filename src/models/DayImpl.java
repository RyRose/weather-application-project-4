package models;


import java.util.Date;
import interfaces.Day;

public class DayImpl implements Day {
	
	Date date;
	double humidity;
	double wind_speed;
	double high_temp;
	double low_temp;
	double current_temp;
	
	public DayImpl() {}
	
	public DayImpl(long unixTime, double humidity, double wind_speed, double high_temp, double low_temp) {
		this.date = new java.util.Date( unixTime );
		this.humidity = humidity;
		this.wind_speed = wind_speed;
		this.high_temp = high_temp;
		this.low_temp = low_temp;
		this.current_temp = (Math.round(((low_temp + high_temp)/2)*100))/100; 
		//rounded to two decimals
	}

	@Override
	public String toString() {
		return "DayImpl: { " + "date: " + date + ", humidity: " + humidity + ", windSpeed: " + wind_speed + 
				", high_temp: " + high_temp + ", low_temp: " + low_temp + ", current_temp: " + current_temp + " }";
	}

	@Override
	public void setDate(int num) {date = new java.util.Date((long)num*1000); }
	@Override
	public void setMin(double temp) {low_temp = temp; }
	@Override
	public void setMax(double temp) {high_temp  = temp;	}
	@Override
	public void setCurrent(double temp) {current_temp  = temp;	}
	@Override
	public void setHumidity(double currentHumidity) {humidity = currentHumidity;}
	@Override
	public void setSpeed(double speed) {wind_speed = speed;	}
	
	
	@Override
	public Date getDate() {return date;}
	@Override
	public double getHumidity() {return humidity;}
	@Override
	public double getMin() {return low_temp;}
	@Override
	public double getMax() {return high_temp;}
	@Override
	public double getCurrent() {return current_temp;}
	@Override
	public double getSpeed() {return wind_speed;}
}
