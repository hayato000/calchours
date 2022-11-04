package com.cimmentu.swiss.calchours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class CalchoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalchoursApplication.class, args);
	}

}
