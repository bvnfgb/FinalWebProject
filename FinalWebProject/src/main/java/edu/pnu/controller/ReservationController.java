package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.ReservationDeta;
import edu.pnu.service.ReservationService;

@RestController
public class ReservationController {
	@Autowired
	ReservationService reservationService;
	
	@PostMapping("/user/reserv/add")
	ResponseEntity<?> adawnRsrvt(@RequestHeader("Authorization") String token,@RequestBody ReservationDeta deta){
		int addResult=reservationService.adawnRsrvt(token,deta);
		if(addResult==0)
			return ResponseEntity.ok().build();
		else if(addResult==1)
			return ResponseEntity.badRequest().body("Incomplete item!");
		else
			return ResponseEntity.internalServerError().build();
	}
	@GetMapping("/user/reserv/view")
	ResponseEntity<?> getReservList(@RequestHeader("Authorization") String token,@RequestHeader("deviceId") String deviceId){
		List<ReservationDeta> getReservList=reservationService.getReservList(token, deviceId);
		
		return ResponseEntity.ok(getReservList);
	}
}
