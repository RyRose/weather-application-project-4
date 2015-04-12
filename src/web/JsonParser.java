package web;

import interfaces.Day;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {	
	
	DataAsStringGenerator generator;
	
	public JsonParser() {
	}
	
	public static ArrayList<Day> parseJson( String json ) throws IOException {
		JsonData model = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			model = mapper.readValue(json , JsonData.class); 
		} catch (JsonParseException e) {
			throw new IllegalArgumentException("Invalid json");
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	
		return model.toDays();
	}
	
	
	private static class JsonData {
						
		@JsonProperty("list")
		public JsonDayModel[] models;
		
		@JsonIgnore
		public ArrayList<Day> toDays() throws IOException {
			ArrayList<Day> list = new ArrayList<Day>();
			
			if ( models == null ) {
				throw new IOException();
			}
			for ( JsonDayModel model : models ) {
				list.add( model.toDay() );
			}
			return list;
		}
	}
}
 
