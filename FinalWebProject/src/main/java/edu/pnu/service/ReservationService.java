package edu.pnu.service;

import java.util.HashMap;
import java.util.List;

import edu.pnu.domain.ReservationDeta;

public interface ReservationService {
	int adawnRsrvt(ReservationDeta reservationDeta);
	List<ReservationDeta> getReservList(String deviceId);
	int dltRsrvt(HashMap<String, List<String>> hashMap);
	
}
