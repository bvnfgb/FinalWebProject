package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		@SuppressWarnings("rawtypes")
		List list=awningService.getAwningList(token);
		if(list!=null)
			return ResponseEntity.ok(list);
		else
			return ResponseEntity.badRequest().body("Token Blank");
	}
	@PutMapping("/admin/device/add")
	public ResponseEntity<?> addAwning(@RequestHeader("Authorization") String token,@RequestBody AwningControl awningControl){
		int addResult=awningService.addAwning(token, awningControl);
		if(addResult==1)
			return ResponseEntity.badRequest().body("Incomplete Item");
		else if(addResult==2)
			return ResponseEntity.badRequest().body("invalid place");
		else if(addResult==3)
			return ResponseEntity.internalServerError().body("input exception add awning");
		else
			return ResponseEntity.ok("awning input is successful");
			
		
	}
	@GetMapping("/user/device/view/{deviceId}")
	public ResponseEntity<?> getAwningStat(@RequestHeader("Authorization") String token,@PathVariable String deviceId){
		int getSingleResult= awningService.getAwningStat(token,deviceId);
		if(getSingleResult==1)
			return ResponseEntity.badRequest().body("Send deviceId!");
		else if(getSingleResult==2)
			return ResponseEntity.badRequest().body("Incorrect deviceId!");
		return ;
		
	}
}
