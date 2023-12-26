package edu.pnu.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import edu.pnu.jwt.domain.Member;
import edu.pnu.jwt.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final MemberRepository memberRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String srcToken=request.getHeader("Authorization");
		if(srcToken==null||!srcToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwtToken=srcToken.replace("Bearer ", "");
		
		try {
			String username=JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken).getClaim("username").asString();
			
			Optional<Member> opt=memberRepository.findByLoginId(username);
			
			if(!opt.isPresent()) {
				filterChain.doFilter(request, response);
				return;
			}
			Member findmember=opt.get();
			
			User user=new User(findmember.getLoginId(), findmember.getLoginPassword(), AuthorityUtils.createAuthorityList(findmember.getManageType().toString()));
			System.out.println(user+"=User");
			Authentication auth=new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (TokenExpiredException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
			// TODO: handle exception
		}		
		
		
		
		
		filterChain.doFilter(request, response);

	}

}
