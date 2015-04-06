package web;

import interfaces.Day;

import java.io.IOException;

import models.DayImpl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {			
	
	public static Day[] parseJson( String json ) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return new Day[] {mapper.readValue(json , DayImpl.class)}; 
		} catch (JsonParseException e) {
			throw new IllegalArgumentException("Invalid json");
		} catch (JsonMappingException e) {
			try {
				return mapper.readValue(json, DayImpl[].class); 
			} catch (IOException e1) {
				throw new IllegalArgumentException("Invalid json");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
}
 
