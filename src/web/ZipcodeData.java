package web;

import interfaces.Day;

import java.io.IOException;
import java.util.ArrayList;

public class ZipcodeData {
	
	private static web.JsonParser parsor = new web.JsonParser();
	
	
	public static ArrayList<Day> getDays (String zipCode, int days) throws IOException{
		DataAsStringGenerator datagenerator = new DataAsStringGenerator();
		String data = datagenerator.generateStringForCurrent(zipCode, Integer.toString(days));
		return parsor.parseJson(data);
	}

}
