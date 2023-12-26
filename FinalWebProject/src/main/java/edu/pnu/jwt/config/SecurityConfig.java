package edu.pnu.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import edu.pnu.jwt.filter.JWTAuthenticationFilter;
import edu.pnu.jwt.filter.JWTAuthorizationFilter;
import edu.pnu.jwt.persistence.MemberRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private MemberRepository memberRepository;
	@Bean
	PasswordEncoder  passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	@Bean
	SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable());
		http.cors(cors->cors.disable());
		
		http.authorizeHttpRequests(auth->auth
				.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("CEO","DIREACTOR","MANAGER","TEST")
				.requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated()
				
				
				.anyRequest().permitAll());
		
		http.formLogin(frmLogin->frmLogin.disable());
//		http.oauth2Login(oauth2->{oauth2.loginPage("/login")
//			;});
		http.httpBasic(basic->basic.disable());
		
		http.sessionManagement(ssmn->ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository),AuthorizationFilter.class);
		
		
		
		return http.build();
	}
	
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
	 @Bean
	    FilterRegistrationBean<CorsFilter> corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        // Allow all origins, methods, and headers for simplicity
	        config.addAllowedOrigin("*");
	        config.addAllowedMethod("*");
	        config.addAllowedHeader("*");
	        config.addExposedHeader("*");
	        
	        source.registerCorsConfiguration("/**", config);
	        
	        
	        
	        CorsFilter corsFilter = new CorsFilter(source);

	        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(corsFilter);
	        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Set the highest precedence

	        return registrationBean;
	    }
}
