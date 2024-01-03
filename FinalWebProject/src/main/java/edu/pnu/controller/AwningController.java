package edu.pnu.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import edu.pnu.domain.AwningControl;
import edu.pnu.service.AwningService;
import edu.pnu.service.other.AddModify;
import edu.pnu.service.other.AwningStatResult;
import edu.pnu.socket.WebSocketService;
@RestController
@SuppressWarnings("rawtypes")
public class AwningController {
	@Autowired
	private AwningService awningService;
	@Autowired
	private WebSocketService webSocketService;
	@PostMapping("/user/map")
	public ResponseEntity<?> getAwningList(@RequestHeader("Authorization") String token) {
		
		
		List list=awningService.getAwningList(token);
		if(list!=null)
			return ResponseEntity.ok(list);
		else
			return ResponseEntity.noContent().build();
	}
	@PostMapping("/admin/device/add")
	public ResponseEntity<?> addAwning(@RequestHeader("Authorization") String token,@RequestBody AwningControl awningControl){
		int addResult=awningService.addAwning(token, awningControl,AddModify.ADD);
		if(addResult==1)
			return ResponseEntity.badRequest().body("Incomplete Item");
		else if(addResult==2)
			return ResponseEntity.badRequest().body("invalid place");
		else if(addResult==3)
			return ResponseEntity.internalServerError().body("input exception add awning");
		else
			return ResponseEntity.ok("awning input is successful");
			
		
	}
	
	@GetMapping("/user/device/view")
	public ResponseEntity<?> getAwningLStatList(@RequestHeader("Authorization") String token
			,@RequestParam HashMap<String,String> paramMap){
		
		List list=awningService.getAwningLStatList(token,paramMap);
		if(list!=null)
			return ResponseEntity.ok(list);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/user/device/view/{deviceId}")
	public ResponseEntity<?> getAwningStat(@RequestHeader("Authorization") String token,@PathVariable String deviceId){
		AwningStatResult getSingleResult= awningService.getAwningStat(token,deviceId);
		if(getSingleResult==AwningStatResult.DEVICE_ID_NULL_OR_BLANK)
			return ResponseEntity.badRequest().body("Send deviceId!");
		else if(getSingleResult==AwningStatResult.DEVICE_NOT_FOUND)
			return ResponseEntity.badRequest().body("Incorrect deviceId!");
		else 
			return ResponseEntity.ok(getSingleResult.getAwningIndividualStatus());
		
	}
	@DeleteMapping("/admin/device/del")
	public ResponseEntity<?> deleteAwningSeleted(@RequestHeader("Authorization") String token,@RequestBody List<String> list){
		
		int deleteCount=awningService.deleteAwningSeleted(token, list);
		if(deleteCount==0)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok("Awning deletion successful!["+deleteCount+"]");
	}
	@PutMapping("/admin/device/mod")
	public ResponseEntity<?> modAwning(@RequestHeader("Authorization") String token,@RequestBody AwningControl awningControl){
		int modResult=awningService.addAwning(token, awningControl, AddModify.MODIFY);
		if(modResult==4)
			return ResponseEntity.badRequest().body("No Awning Id!");
		return ResponseEntity.ok("Modification successful!");
		
	}
	@PostMapping("/user/test/send")
	public ResponseEntity<?> sendText(@RequestHeader("Authorization") String token,@RequestBody HashMap<String,String> jsonObject ){
		int sendResult=webSocketService.sendTest(jsonObject);
		if(sendResult!=0)
			return ResponseEntity.internalServerError().body("Sending ER");
		return ResponseEntity.ok("Sending success");
		
	}
}
