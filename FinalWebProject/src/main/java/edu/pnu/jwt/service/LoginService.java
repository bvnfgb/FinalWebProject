package edu.pnu.jwt.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.jwt.domain.Member;
@Service
public class LoginService {
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	public String loginProc(Member member) throws Exception{
		
		Authentication authToken=new UsernamePasswordAuthenticationToken(member.getLoginId(), member.getLoginPassword());
		
		AuthenticationManager authenticationManager=authenticationConfiguration.getAuthenticationManager();
		
		Authentication auth=authenticationManager.authenticate(authToken);
		
		
		String token=JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10))
				.withClaim("username", member.getLoginId())
				.sign(Algorithm.HMAC256("edu.pnu.jwt"));
		System.out.println("auth "+auth);
		return "Bearer "+token;
	}
}
