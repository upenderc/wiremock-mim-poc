package com.mim.poc;

import java.net.MalformedURLException;
import java.net.URL;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

public class FailOverTransformer extends ResponseDefinitionTransformer {

	public static final String FAIL_OVER_TRANSFORMER = "fail-over-transformer";
	public static final String WEATHER_PROXY_URL = "weather-proxy-url";
	public static final String WEATHER_MOCK_RES_BODY="weather-mock-res-body";

	@Override
	public String getName() {
		
		return FAIL_OVER_TRANSFORMER;
	}

	@Override
	public ResponseDefinition transform(Request request, ResponseDefinition responseDefinition, FileSource files,
			Parameters parameters) {
		String proxyURl = parameters.getString(WEATHER_PROXY_URL);
		boolean found=false;
		try {
			 found = HttpPingTestUtil.doesURLExist(new URL(proxyURl+request.getUrl()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return found ? ResponseDefinitionBuilder
		                .like(responseDefinition)
		                .proxiedFrom(proxyURl)
		                .build()
					 : new ResponseDefinitionBuilder()
					 	.withStatus(200)
					 	.withHeader("Content-Type", "text/plain")
					 	.withBody(parameters.getString(WEATHER_MOCK_RES_BODY))
					 	.build();
	}

}
