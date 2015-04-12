package models;


import java.util.Date;

import interfaces.Day;

public class DayImpl implements Day {
	
	long date;
	double humidity;
	double wind_speed;
	double high_temp;
	double low_temp;
	
	public DayImpl() {}
	
	public DayImpl(long unixTime, double humidity, double wind_speed, double high_temp, double low_temp) {
		this.date = unixTime;
		this.humidity = humidity;
		this.wind_speed = wind_speed;
		this.high_temp = high_temp;
		this.low_temp = low_temp;

	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof DayImpl ) {
			DayImpl other = (DayImpl) obj;
			return other.getDate().equals(getDate()) &&
					other.getHumidity() == getHumidity() &&
					other.getSpeed() == getSpeed() &&
					other.getMax() == getMax() &&
					other.getMin() == getMin();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "DayImpl: { " + "date: " + getDate() + ", humidity: " + humidity + ", windSpeed: " + wind_speed + 
				", high_temp: " + high_temp + ", low_temp: " + low_temp + " }";
	}

	@Override
	public void setDate(long num) {date = num; }
	@Override
	public void setMin(double temp) {low_temp = temp; }
	@Override
	public void setMax(double temp) {high_temp  = temp;	}
	@Override
	public void setHumidity(double currentHumidity) {humidity = currentHumidity;}
	@Override
	public void setSpeed(double speed) {wind_speed = speed;	}
	
	
	@Override
	public Date getDate() {return new java.util.Date(date);}
	@Override
	public double getHumidity() {return humidity;}
	@Override
	public double getMin() {return low_temp;}
	@Override
	public double getMax() {return high_temp;}
	@Override
	public double getCurrent() {return (high_temp + low_temp)/2;}
	@Override
	public double getSpeed() {return wind_speed;}
}
