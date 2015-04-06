package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonGenerator {
	UrlOpener opener = new UrlOpener();
	String scheme = "http://", authority  = "api.openweathermap.org";
	String pathForecast = "/data/2.5/forecast/daily?q=", pathCurrent = "/data/2.5/find?q=";	
	String querryImperial = ",%20us&units=imperial&cnt=", querryMetric = ",%20us&units=metric&cnt=";
	String querryLike = "&type=like", querryAccurate = "&type=accurate";
	String json;
	
	public JsonGenerator(){}
	
	public String generateString(String zipCode, String numberOfDays) throws IOException{
		String linkToHandle = scheme+authority+pathForecast+zipCode+querryImperial+numberOfDays+querryAccurate;
		//typical link looks like these two below
		//http://api.openweathermap.org/data/2.5/forecast/daily?q=72032,%20us&units=imperial&cnt=16&type=accurate
		BufferedReader data = opener.openUrl(linkToHandle);
		String line;
		while((line = data.readLine()) != null){
		    json = line;
		}
		System.out.println(json);
		return json;
	}
}
