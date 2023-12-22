package edu.pnu.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

import edu.pnu.domain.AwningControl;
import edu.pnu.jwt.persistence.MemberRepository;
import edu.pnu.persistence.AwningControlRepository;
@Service
public class AwningServicelmpl implements AwningService {
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	AwningControlRepository awningControlRepository;

	@Override
	public ResponseEntity<?> getAwningList(String token) {// /user/map
		if(isBlankToken(token))
			return ResponseEntity.badRequest().body("Token Blank");
//		if(!isValidToken(token))
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("TOKEN expired");
		List list= awningControlRepository.findAllByUserMap();//리스트 찾아내기
		
		return ResponseEntity.ok(list);
	}
	
	

	@Override
	public ResponseEntity<?> addAwning(String token, AwningControl awningControl) {
		if(!checkAwningControlValid(awningControl))
			return ResponseEntity.badRequest().body("Incomplete Item");
		return null;
	}
	
	
	
	
	private boolean checkAwningControlValid(AwningControl awningControl) {
//		awningControl.
		return false;
	}



	private boolean isBlankToken(String token) {
		if(token==null)
			return true;
		if(token.isEmpty()||token.isBlank())
			return true;
		return false;
		
	}
}
