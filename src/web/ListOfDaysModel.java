package web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
 
public class ListOfDaysModel{
    private String dt;
    private String pressure;
    private String humidity;
    private String speed;
    private Days[] days;
    
    
    private Map<String , Object> otherProperties = new HashMap<String , Object>();
 
    public String getdate() {
        return dt;
    }
 
    public void setDate(String date) {
        this.dt = date;
  
    }
    
    public String getPressure() {
        return pressure;
    }
 
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    
    public String getHumidity(){
    	return humidity;
    }
    public void setHumidity(String humidity){
    	this.humidity = humidity;
    }
    
    public String getWindSpeed(){
    	return speed;
    }
    
    public void setwindSpeed(String speed){
    	this.speed = speed;
    }
    public Object get(String name) {
        return otherProperties.get(name);
    }
    
    public Days[] getObjects(){
    	return days;
    }
    
    @Override
    public String toString() {
    	return dt + " " + pressure + " " + humidity + " " + speed;
    }
 
}