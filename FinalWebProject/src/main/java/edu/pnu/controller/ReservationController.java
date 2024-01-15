package edu.pnu.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.ReservationDeta;
import edu.pnu.service.ReservationService;

@RestController
public class ReservationController {
	@Autowired
	ReservationService reservationService;
	
	@PostMapping("/user/reserv/add")
	ResponseEntity<?> adawnRsrvt(@RequestBody ReservationDeta deta){
		int addResult=reservationService.adawnRsrvt(deta);
		if(addResult==0)
			return ResponseEntity.ok().build();
		else if(addResult==1)
			return ResponseEntity.badRequest().body("Incomplete item!");
		else
			return ResponseEntity.internalServerError().build();
	}
	@GetMapping("/user/reserv/view")
	ResponseEntity<?> getReservList( String deviceId){
		List<ReservationDeta> getReservList=reservationService.getReservList( deviceId);
		for(ReservationDeta deta:getReservList) {
			System.out.println("->"+deta.toString());
			
		}
		return ResponseEntity.ok(getReservList);
	}
	@DeleteMapping("/user/reserv/del")
	ResponseEntity<?> delRsrvt(@RequestBody HashMap<String, List<String>> hashMap){
		reservationService.dltRsrvt( hashMap);
		return null;
		
	}
}
