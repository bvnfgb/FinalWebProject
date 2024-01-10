package edu.pnu;

import java.time.ZoneId;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  
public class FinalWebProjectApplication {

	public static void main(String[] args)  {
		SpringApplication.run(FinalWebProjectApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));
	}
	

}
