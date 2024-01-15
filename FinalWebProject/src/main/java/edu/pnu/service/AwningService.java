package edu.pnu.service;

import java.util.HashMap;
import java.util.List;

import edu.pnu.domain.AwningStatusLog;
import edu.pnu.domain.Event;
import edu.pnu.domain.other.AwningControl;
import edu.pnu.domain.other.AwningUserMapDTO;
import edu.pnu.service.other.AddModify;
import edu.pnu.service.other.AwningStatResult;
@SuppressWarnings("rawtypes")
public interface AwningService {
	
	List<AwningUserMapDTO> getAwningList();
	int addAwning( AwningControl awningControl,AddModify addModify);
	AwningStatResult getAwningStat( String deviceId);
	List getAwningLStatList(HashMap<String,String> paramMap);
	int deleteAwningSeleted(List<String> list);
	void addAwningLog(AwningStatusLog awningStatusLog);
	HashMap<String, Integer> quickSummaryReply();
	int saveEvent(Event event);
}
