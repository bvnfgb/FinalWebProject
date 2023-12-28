package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.ManageArea;
import edu.pnu.jwt.domain.ManageType;
import edu.pnu.jwt.domain.Member;
import edu.pnu.jwt.persistence.MemberRepository;
import edu.pnu.persistence.ManageAreaRepository;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	ManageAreaRepository manageAreaRepository;
	@Autowired
	MemberRepository memberRepository;
	
	//이하 구현서비스
	@Override
	public int addUser(String token,Member member) {
		if(!checkMemberValid(member))
			return 1;
		List<ManageArea> areaList= manageAreaRepository.findByCity(member.getManagementArea1());
		Integer ManageArea1234=setManagementAreaId(member,areaList);
		if (ManageArea1234==null)
			return 1;
		member=Member.builder().admnsType(member.getAdmnsType()).contact(member.getContact()).depart(member.getDepart())
		.loginId(member.getLoginId()).loginPassword(member.getLoginPassword()).manageArea(ManageArea1234)
		.manager(member.getManager()).rank_a(member.getRank_a()).manageType(ManageType.ROLE_TEST).build();
		memberRepository.save(member);
		return 0;
	}
	@Override
	public int delUser(String token, String loginId) {
		Optional<Member> optional =memberRepository.findByLoginId(loginId);
		if(optional.isEmpty())
			return 1;
		memberRepository.deleteByLoginId(loginId);
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getUser(String token, String loginId) {
		Member member=memberRepository.findByLoginId(loginId).get();
		
		// TODO Auto-generated method stub
		return 0;
	}
	//이하 메소드
	private Integer setManagementAreaId(Member member,List<ManageArea> areaList) {
		try {
			for(ManageArea area:areaList) {
			if(area.getCity2()==null)
				return area.getCityId();
			if(area.getCity2()==member.getManagementArea2())
				return area.getCityId();
		}
		} catch (NullPointerException e) {
			return null;
		}
		
		return null;
		
	}
	
	private boolean checkMemberValid(Member member) {//멤버 객체 입력 유효체크
		if(isInvalidString(member.getDepart()))
			return false;
		if(isInvalidString(member.getLoginId()))
			return false;
		if(isInvalidString(member.getLoginPassword()))
			return false;
		if(isInvalidString(member.getManagementArea1()))
			return false;
		if(isInvalidString(member.getRank_a()))
			return false;
		if(isInvalidString(member.getManager()))
			return false;
		if(member.getContact()==null)
			return false;
		
		return true;
	}
	
	private boolean isInvalidString(String string) {//스트링 유효 체크
		if(string==null)
			return true;
		if(string.isEmpty()||string.isBlank())
			return true;
		return false;
	}
	


	
}
