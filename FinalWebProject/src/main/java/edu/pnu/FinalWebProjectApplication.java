package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication  (exclude={
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
        }
)
public class FinalWebProjectApplication {

	public static void main(String[] args)  {
		SpringApplication.run(FinalWebProjectApplication.class, args);
		
	}
	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				WebMvcConfigurer.super.addCorsMappings(registry);
				registry.addMapping("/**").allowedOrigins("localhost");
			}
		};
	}

}
