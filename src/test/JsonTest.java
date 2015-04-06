package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import web.JsonParser;

public class JsonTest {
	private web.JsonParser parser;
	private web.JsonGenerator generator = new web.JsonGenerator();
	
	
	//@Before
	//public void setup() throws IOException {
		//parsor = new web.JsonParser("72032");
		//System.out.println("Ok");
	//}
	
	@Test
	public void test1() throws IOException {
		System.out.println("ok");
		String toParse =  generator.generateStringForForecast("72032", "16");
		System.out.println(toParse);
		
		System.out.println("hello two");
		
	}

}
