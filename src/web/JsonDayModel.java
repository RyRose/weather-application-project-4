package web;

import interfaces.Day;

import models.DayImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class JsonDayModel {
		
    @JsonProperty("dt")
    public long date;
    
    @JsonProperty("pressure")
    public double pressure;
    
    @JsonProperty("humidity")
    public double humidity;
    
    @JsonProperty("speed")
    public double windSpeed;
    
    @JsonProperty("temp")
    public Temperature temp;
    
    @JsonIgnore
    @Override
    public String toString() {
    	return "date: " + date + " | " + "pressure: " + pressure + " | " + "humidity: " +
    			humidity + " | " + "wind: " + windSpeed + " | " + "temp: " + temp;
    }

    @JsonIgnore
    public Day toDay() {
    	return new DayImpl(date*1000, humidity, windSpeed, temp.maximumTemperature, temp.minimumTemperature);
    }

    private static class Temperature {
    	    	
    	@JsonProperty("min")
    	public double minimumTemperature;
    	
    	@JsonProperty("max")
    	public double maximumTemperature;
    	
    	@Override
    	public String toString() {
    		return "min: " + minimumTemperature + " | " + "max: " + maximumTemperature;
    	}
    }

 
}