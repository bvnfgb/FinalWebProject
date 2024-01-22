package edu.pnu.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.ReservationDeta;
import edu.pnu.persistence.ReservationDetaRepository;
@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	ReservationDetaRepository detaRepository;
	//이하 구현 서비스
	@Override
	public int adawnRsrvt(ReservationDeta detail) {
		
		if(isInvalidReserv(detail))
			return 1;
		detail=setDetail(detail);
		try {
			detaRepository.save(detail);
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
	@Override
	public List<ReservationDeta> getReservList( String deviceId) {
		List<ReservationDeta> findList= detaRepository.findByAwningDeviceId(deviceId);
		return findList;
	}
	@Override
	public int dltRsrvt(HashMap<String, List<String>> hashMap) {
		// /user/reserv/del
		if(hashMap.get("deviceId")!=null) {
			List<String> deviceId=hashMap.get("deviceId");
			System.out.println("deviceId "+deviceId);
		}
		return 0;
	}

	//이하 메소드
	private ReservationDeta setDetail(ReservationDeta detail) {
		detail=ReservationDeta.builder().awningDeviceId(detail.getAwningDeviceId())
				.repeatDay(detail.getRepeatDay()!=null?detail.getRepeatDay():null)
				.startDate(detail.getStartDate()!=null?detail.getStartDate():null)
				.finshDate(detail.getFinshDate()!=null?detail.getFinshDate():null)
				.reservationItems(detail.getReservationItems())
				.reservationMethod(detail.getReservationMethod())
				.repeatDayTime(detail.getRepeatDayTime()!=null?detail.getRepeatDayTime():null)
				.build();
		return detail;
	}
	private boolean isInvalidReserv(ReservationDeta detail) {
		if(isInvalidString(detail.getAwningDeviceId()) )
			return true;
		if(isInvalidString(detail.getReservationItems()))
			return true;
		if(isInvalidString(detail.getReservationMethod()))
			return true;
		if(detail.getRepeatDay()==null&&(detail.getFinshDate()==null||detail.getStartDate()==null))
			return true;
		return false;
	}
	private boolean isInvalidString(String string) {
		if(string==null)
			return true;
		if(string.isEmpty()||string.isBlank())
			return true;
		return false;
		
	}
	
}
