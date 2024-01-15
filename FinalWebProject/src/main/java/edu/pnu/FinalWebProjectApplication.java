package edu.pnu;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication  
public class FinalWebProjectApplication {

	public static void main(String[] args)  {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Tokyo")));
		@SuppressWarnings("unused")
		OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.ofHours(9));
		SpringApplication.run(FinalWebProjectApplication.class, args);
		
	}
	

}
