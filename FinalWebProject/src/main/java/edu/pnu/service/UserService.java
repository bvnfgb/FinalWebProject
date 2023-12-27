package edu.pnu.service;

import edu.pnu.jwt.domain.Member;

public interface UserService {
	int addUser(String token,Member member);
}
