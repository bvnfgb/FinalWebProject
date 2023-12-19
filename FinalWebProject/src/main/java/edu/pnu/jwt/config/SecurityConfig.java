package edu.pnu.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.pnu.jwt.domain.ManageType;
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
				.requestMatchers(new AntPathRequestMatcher("/member/**")).authenticated()
				.requestMatchers(new AntPathRequestMatcher("/manager/**")).hasAnyRole("CEO","DIREACTOR","MANAGER","TEST")
				.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("CEO","DIREACTOR","TEST")
				.anyRequest().permitAll());
		
		http.formLogin(frmLogin->{frmLogin.loginPage("/login")
			.defaultSuccessUrl("/loginSuccess",true);});
//		http.oauth2Login(oauth2->{oauth2.loginPage("/login")
//			;});
		http.httpBasic(basic->basic.disable());
		
		http.sessionManagement(ssmn->ssmn.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepository),AuthorizationFilter.class);
		return http.build();
	}
}
