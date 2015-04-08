package web;

import interfaces.Day;

import java.io.IOException;
import java.util.ArrayList;

public class ZipcodeData {
	
	private static web.JsonParser parser;
	private static web.DataAsStringGenerator generator = new web.DataAsStringGenerator();
	
	
	public static ArrayList<Day> getDays (String zipCode, int days) throws IOException{
		String data = generator.generateStringForForecast(zipCode, Integer.toString(days));
		return parser.parseJson(data);
	}

}
