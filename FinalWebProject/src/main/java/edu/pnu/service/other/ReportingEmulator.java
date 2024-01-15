package edu.pnu.service.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.pnu.controller.AwningController;
import edu.pnu.domain.AwningStatusLog;
import edu.pnu.domain.Event;
import edu.pnu.service.AwningService;

@Component
public class ReportingEmulator {
	
	
	@Autowired
	AwningController awningController;
	
	@Scheduled(fixedRate = 5000)
	public void report() {
		AwningStatusLog awningStatusLog=AwningStatusLog.builder()
				.awningId(1)
				.deviceId("00001")
				.controlId(10010).build();
		awningController.awnngRcvd(awningStatusLog);
	}
	@Scheduled(fixedRate = 30000)
	public void event() {
		System.out.println("event");
		Event event=Event.builder()
				.awningId(1)
				.eventType("변경 없음")
				.eventType2("배터리 상태 정상")
				.eventType3("모터 상태 정상")
				.type("보냄")
				.build();
		
		awningController.sendText(event);
	}
}
