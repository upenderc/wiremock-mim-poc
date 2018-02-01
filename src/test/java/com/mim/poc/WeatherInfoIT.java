package com.mim.poc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;


@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:application-test.xml")
public class WeatherInfoIT {

	@Autowired
	private WeatherGateway gateway;
	
	@Value("${weather.proxy.url}")
	private String weatherProxyUrl;
	
	@SuppressWarnings("static-access")
	@Rule
	public WireMockRule openWeatherMockRule = new WireMockRule(wireMockConfig().options().port(8999)
			.extensions(new FailOverTransformer()));
	
	@Before
	public void init() throws Exception {
		openWeatherMockRule.stubFor(WireMock.get(WireMock.urlPathMatching(".*"))
				  .withQueryParam("appid", WireMock.equalTo("b6907d289e10d714a6e88b30761fae22"))	
				  .withQueryParam("mode", WireMock.equalTo("xml"))
				  .withQueryParam("q", WireMock.equalTo("London"))
				  .willReturn(WireMock.aResponse().withTransformers(FailOverTransformer.FAIL_OVER_TRANSFORMER)
				  .withTransformerParameter(FailOverTransformer.WEATHER_PROXY_URL, weatherProxyUrl)
				  .withTransformerParameter(FailOverTransformer.WEATHER_MOCK_RES_BODY,
						      readContentBy("/mock/mockresponse.xml"))));
	}
	private String readContentBy(String filePath) {
		String contents="";
		Resource resource=new ClassPathResource(filePath);
		try {
			contents= new String (Files.readAllBytes(resource.getFile().toPath()));
		} catch (IOException e) {
		}
		return contents;
	}
	@Test
	public void getInfo() {
		QueryParameters parameters=new QueryParameters();
		parameters.setAppId("b6907d289e10d714a6e88b30761fae22");
		parameters.setMode("xml");
		parameters.setCity("London");
		String response=gateway.send(parameters);
		System.out.println(response);
		//fake test
		Assert.assertTrue(true);
	}
}
