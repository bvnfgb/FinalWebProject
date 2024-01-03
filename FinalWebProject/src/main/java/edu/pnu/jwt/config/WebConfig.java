package edu.pnu.jwt.config;


//@Configuration

public class WebConfig {//cors 설정
	
	
//	@Bean
//	PasswordEncoder  passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	
	
//	@Bean
//	WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				// TODO Auto-generated method stub
//				WebMvcConfigurer.super.addCorsMappings(registry);
//				registry.addMapping("/**").allowedOrigins("http://10.125.121.218:3000")
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
//                .allowCredentials(true);
//			}
//		};
//	}
//	 @Bean
//	 CorsConfigurationSource corsFilter() {
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        CorsConfiguration config = new CorsConfiguration();
//	        // Allow all origins, methods, and headers for simplicity
//	        config.addAllowedOrigin("*");
//	        config.addAllowedMethod("*");
//	        config.addAllowedHeader("*");
//	        config.addExposedHeader("*");
//	        
//	        source.registerCorsConfiguration("/**", config);
//	        return source;
//	        
////	        
////	        CorsFilter corsFilter = new CorsFilter(source);
////
////	        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(corsFilter);
////	        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Set the highest precedence
////
////	        return registrationBean;
//	    }
}
