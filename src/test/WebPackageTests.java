package test;

import java.io.IOException;

import org.junit.Test;

import web.JsonParser;
import static org.junit.Assert.*;
public class WebPackageTests {
	private int numOfDays = 16;
	private web.DataAsStringGenerator generator = new web.DataAsStringGenerator();
	private String conwayArUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=72032&units=metric&cnt=7";

	@Test
	public void testTheParser() throws IOException {
		String toParse =  generator.generateStringForForecast("72032", Integer.toString(numOfDays));
		int size = JsonParser.parseJson(toParse).size();
		assertEquals(size, numOfDays);	
	}
	@Test
	public void testTheUrlOpener() throws IOException{
		System.out.println("The bufferReader memory location after reading data from the web");
		System.out.println(web.UrlOpener.openUrl(conwayArUrl).toString());	
	}
	@Test
	public void testJsonDecoding() throws IOException{
		String toParse =  generator.generateStringForForecast("72032", Integer.toString(numOfDays));
		boolean trueOrFalse = toParse.contains("Conway");
		System.out.println("condition in testJsonDecoding is: " + trueOrFalse);
		assertTrue(trueOrFalse);
	}

}
