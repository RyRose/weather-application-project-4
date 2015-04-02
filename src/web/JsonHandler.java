package web;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonHandler {
	UrlOpener opener = new UrlOpener();
	
	public JsonHandler(){}
	
	public String generateString(String zipCode, String numberOfDays) throws IOException{
		String linkToHandle = "link from openweathermap";
		//typical link looks like the one below
		//http://api.openweathermap.org/data/2.5/forecast/daily?q=72032,%20us&units=metric&cnt=16
		BufferedReader data = opener.openUrl(linkToHandle);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String json = mapper.writeValueAsString(data);
		System.out.println(json);
		return json;
	}
	


	
	

}
