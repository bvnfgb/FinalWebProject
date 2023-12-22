package edu.pnu.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.jwt.domain.Member;
import edu.pnu.jwt.persistence.MemberRepository;
@Service
public class SecurityUserDetailService implements UserDetailsService {
	@Autowired
	private MemberRepository memRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member=memRepo.findByLoginId(username).orElseThrow(()->
		new UsernameNotFoundException("Not Found!")); 
		// TODO Auto-generated method stub
		return new User(member.getLoginId(),member.getLoginPassword(),AuthorityUtils.createAuthorityList(member.getManageType().toString()));
	}

}
