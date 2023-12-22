package edu.pnu.jwt.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.jwt.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
	
		ObjectMapper mapper=new ObjectMapper();
		Member member=null;
		try {
			member=mapper.readValue(request.getInputStream(), Member.class );
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Authentication authToken= new UsernamePasswordAuthenticationToken(member.getLoginId(), member.getLoginPassword());
		System.out.println("authToken:"+authToken);
		Authentication auth=authenticationManager.authenticate(authToken);
		System.out.println("auth: "+ auth);
		return auth;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		User user=(User)authResult.getPrincipal();
		System.out.println(user+"user");
		
		String token=JWT.create().withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*7))
				.withClaim("username",user.getUsername())
				.sign(Algorithm.HMAC256("edu.pnu.jwt"));
		
		String refreshToken = JWT.create()
	            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Refresh token expiration time (e.g., 7 days)
	            .withClaim("username", user.getUsername())
	            .sign(Algorithm.HMAC256("edu.pnu.refresh.jwt"));
		
		response.addHeader("Authorization", "Bearer "+token);
		response.addHeader("Refresh-Token", "Bearer "+refreshToken);
	}
}
