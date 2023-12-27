package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.jwt.domain.Member;
import edu.pnu.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/admin/use/add")
	public ResponseEntity<?> addUser(@RequestHeader("Authorization") String token,@RequestBody Member member){
		
		
		return null;
		
	}
}
