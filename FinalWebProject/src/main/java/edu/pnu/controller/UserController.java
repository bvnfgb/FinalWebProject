package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.jwt.domain.Member;
import edu.pnu.service.UserService;
import edu.pnu.service.other.AddModify;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/admin/use/add")
	public ResponseEntity<?> addUser(@RequestHeader("Authorization") String token,@RequestBody Member member){
		
		int addResult=userService.addUser(token, member,AddModify.ADD);
		if(addResult==1)
			return ResponseEntity.badRequest().body(null);
		return ResponseEntity.ok().build();
		
	}
	@DeleteMapping("/admin/use/del")
	public ResponseEntity<?> delUser(@RequestHeader("Authorization")String token,@RequestBody String loginId){
		int delResult=userService.delUser(token, loginId);
		switch (delResult) {
		case 1:
			return ResponseEntity.noContent().build();
		default:
			return ResponseEntity.ok().build();
		}
		
	}
	@GetMapping("/user/use/view")
	public ResponseEntity<?> getUserList(@RequestHeader("Authorization")String token){
		List<Member> list=userService.getUserList(token);
		if(list==null)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(list);
	}
//	@PutMapping("/admin/use/mod")
//	public ResponseEntity<?> modUserList
}
