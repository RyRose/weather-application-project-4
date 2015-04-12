package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlOpener {
	public UrlOpener(){}
	
	public static BufferedReader openUrl(String link) throws IOException{
		URL urlToOpen = new URL(link);
		URLConnection yc =  urlToOpen.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
		return in;
		
	}

}
