package edu.pnu.service;

import java.util.List;

import edu.pnu.jwt.domain.Member;
import edu.pnu.service.other.AddModify;

public interface UserService {
	int addUser(String token,Member member,AddModify addModify);
	int delUser(String token,String loginId);
	List<Member> getUserList(String token);
}
