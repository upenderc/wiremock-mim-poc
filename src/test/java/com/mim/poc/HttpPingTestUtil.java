package com.mim.poc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPingTestUtil {
	public static boolean doesURLExist(URL url)
	{
		boolean available=false;
		try {
	    HttpURLConnection.setFollowRedirects(false);
	    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	    httpURLConnection.setRequestMethod("HEAD");
	    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
	    int responseCode = httpURLConnection.getResponseCode();
	    available = responseCode == HttpURLConnection.HTTP_OK || responseCode==HttpURLConnection.HTTP_BAD_METHOD;
		} catch(IOException e) {
			
		}
		return available;
	}
}
