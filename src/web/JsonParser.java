package web;

import interfaces.Day;






import java.io.IOException;
import java.util.HashMap;

import models.DayImpl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class JsonParser {	
	
	JsonGenerator generator;
	
	public JsonParser(String zipCode) throws IOException{
		String toParse = generator.generateStringForForecast(zipCode, "16");
		parseJson(toParse);
	}
	
	public static Object parseJson( String json ) {
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		ListOfDaysModel model;
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			
			map = mapper.readValue(json , Map.class); 
			System.out.println(map.toString());
			System.out.println("we are in ");
		} catch (JsonParseException e) {
			throw new IllegalArgumentException("Invalid json");
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	
		return map;
	}
}
 
