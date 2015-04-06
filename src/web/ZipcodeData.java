package web;

import interfaces.Day;

import java.io.IOException;
import java.util.ArrayList;

public class ZipcodeData {
	
	public static ArrayList<Day> getDays (String zipCode, int days) throws IOException{
		DataAsStringGenerator datagenerator = new DataAsStringGenerator();
		String data = datagenerator.generateStringForCurrent(zipCode, Integer.toString(days));
		return JsonParser.parseJson(data);
	}

}
