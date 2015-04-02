package web;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonHandler {
	UrlOpener opener = new UrlOpener();
	
	public JsonHandler(){}
	
	public String generateString() throws IOException{
		String linkToHandle = "any link we want to open goes here";
		BufferedReader data = opener.openUrl(linkToHandle);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String json = mapper.writeValueAsString(data);
		System.out.println(json);
		return json;
	}
	

	
	

}
