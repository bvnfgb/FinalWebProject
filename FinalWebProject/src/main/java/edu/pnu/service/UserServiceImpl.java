package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.ManageArea;
import edu.pnu.jwt.domain.ManageType;
import edu.pnu.jwt.domain.Member;
import edu.pnu.jwt.persistence.MemberRepository;
import edu.pnu.persistence.ManageAreaRepository;
import edu.pnu.service.other.AddModify;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	ManageAreaRepository manageAreaRepository;
	@Autowired
	MemberRepository memberRepository ;
	
	//이하 구현서비스
	@Override
	public int addUser(String token,Member member,AddModify addModify) {
		
		if(!checkMemberValid(member))
			return 1;
		if(addModify.getCode()==AddModify.MODIFY.getCode()) {
			if(member.getUserId()==null)
				return 1;
		}
		List<ManageArea> areaList= manageAreaRepository.findByCity(member.getManagementArea1());
		
		
		
		Integer ManageArea1234=setManagementAreaId(member,areaList);
		
		
		if (ManageArea1234==null)
			return 1;
		
		member=memberSettings(member, ManageArea1234, addModify);
		
		memberRepository.save(member);
		return 0;
	}
	
	@Override
	public int delUser(String token, String loginId) {
		System.out.println(loginId);
		Optional<Member> optional =memberRepository.findByLoginId(loginId);
		if(optional.isEmpty())
			return 1;
		memberRepository.delete(optional.get());
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Member> getUserList(String token) {
		
		List<Member> memberList= memberRepository.findAll();
		if(memberList.isEmpty())
			return null;
		setReturnList(memberList);
		// TODO Auto-generated method stub
		return memberList;
	}
	private void setReturnList(List<Member> memberList) {
		// TODO Auto-generated method stub
		for(Member member:memberList) {
			ManageArea area= manageAreaRepository.findById(member.getManageArea()).get();
			member.setManagementArea1(area.getCity());
			member.setManagementArea2(area.getCity2());
		}
		
	}

	//이하 메소드
	private Member memberSettings(Member member, Integer ManageArea1234, AddModify addModify) {
		int userId= getUserId();
		userId++;
		member=Member.builder().userId(userId)
				.admnsType(member.getAdmnsType()).contact(member.getContact()).depart(member.getDepart())
				.loginId(member.getLoginId()).loginPassword(new BCryptPasswordEncoder().encode(member.getLoginPassword())).manageArea(ManageArea1234)
				.manager(member.getManager()).rank_a(member.getRank_a()).manageType(ManageType.ROLE_TEST).build();
		if(addModify==AddModify.MODIFY) {
			member=Member.builder().userId(member.getUserId())
					.admnsType(member.getAdmnsType()).contact(member.getContact()).depart(member.getDepart())
					.loginId(member.getLoginId()).loginPassword(new BCryptPasswordEncoder().encode(member.getLoginPassword())).manageArea(ManageArea1234)
					.manager(member.getManager()).rank_a(member.getRank_a()).manageType(ManageType.ROLE_TEST).build();
		}
		return member;
	}
	private int getUserId() {
		List<Member> members= memberRepository.findAll();
		if(members==null)
			return 1;
		int userId=2;
		for(Member member:members) {
			if(userId< member.getUserId())
				userId=member.getUserId();
		}
		return userId;
	}

	private Integer setManagementAreaId(Member member,List<ManageArea> areaList) {
		try {
			for(ManageArea area:areaList) {
			if(area.getCity2()==null)
				return area.getCityId();
			if(area.getCity2().equals(member.getManagementArea2()))
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
