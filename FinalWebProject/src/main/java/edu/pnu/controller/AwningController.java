package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.AwningControl;
import edu.pnu.service.AwningService;
@RestController
public class AwningController {
	@Autowired
	private AwningService awningService;
	
	@PostMapping("/user/map")
	public ResponseEntity<?> getAwningList(@RequestHeader("Authorization") String token) {
		
		awningService.getAwningList(token);
		return awningService.getAwningList(token);
	}
	@PutMapping("/admin/device/add")
	public ResponseEntity<?> addAwning(@RequestHeader("Authorization") String token,@RequestBody AwningControl awningControl){
		
		awningService.addAwning(token, awningControl);
		return null;
		
	}
	
}
