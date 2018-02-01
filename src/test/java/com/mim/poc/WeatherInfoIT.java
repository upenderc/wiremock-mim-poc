package com.mim.poc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:application-test.xml")
public class WeatherInfoIT {

	@Autowired
	private WeatherGateway gateway;
	
	@Test
	public void getInfo() {
		QueryParameters parameters=new QueryParameters();
		parameters.setAppId("b6907d289e10d714a6e88b30761fae22");
		parameters.setMode("xml");
		parameters.setCity("London");
		String response=gateway.send(parameters);
		System.out.println(response);
	}
}
