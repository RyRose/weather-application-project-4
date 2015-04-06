package models;

import java.sql.Date;

import interfaces.Day;

public class DayImpl implements Day {
	
	Date date;
	double humidity;
	double wind_speed;
	double high_temp;
	double low_temp;
	
	public DayImpl( String date, double humidity, double wind_speed, double high_temp, double low_temp) {
		this.date = Date.valueOf(date);
		this.humidity = humidity;
		this.wind_speed = wind_speed;
		this.high_temp = high_temp;
		this.low_temp = low_temp;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public String getNameOfDay() {
		return date.toLocalDate().getDayOfWeek().toString(); // date.toString returns "dow mon dd hh:mm:ss zzz yyyy"
	}

	@Override
	public double getHighTemperature() {
		return high_temp;
	}

	@Override
	public double getLowTemperature() {
		return low_temp;
	}

	@Override
	public double getHumidity() {
		return humidity;
	}

	@Override
	public double getWindSpeed() {
		return wind_speed;
	}
	
}
