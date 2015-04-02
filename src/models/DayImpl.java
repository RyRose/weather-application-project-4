package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import interfaces.Day;

public class DayImpl implements Day {
	
	Date date;
	int humidity;
	int wind_speed;
	int high_temp;
	int low_temp;
	
	public DayImpl( String date, int humidity, int wind_speed, int high_temp, int low_temp) {
		
		try {
			this.date = DateFormat.getInstance().parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format.");
		}
		
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
		return date.toString().split(" ")[0]; // date.toString returns "dow mon dd hh:mm:ss zzz yyyy"
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
