package edu.pnu.service;

import java.util.HashMap;
import java.util.List;

import edu.pnu.domain.ReservationDeta;

public interface ReservationService {
	int adawnRsrvt(String token, ReservationDeta reservationDeta);
	List<ReservationDeta> getReservList(String token,String deviceId);
	int dltRsrvt(String token, HashMap<String, List<?>> hashMap);
	
}
