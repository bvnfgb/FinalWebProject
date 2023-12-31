package edu.pnu.service;

import java.util.HashMap;
import java.util.List;

import edu.pnu.domain.AwningControl;
import edu.pnu.persistence.other.AwningUserMap;
import edu.pnu.service.other.AddModify;
import edu.pnu.service.other.AwningStatResult;
@SuppressWarnings("rawtypes")
public interface AwningService {
	
	List<AwningUserMap> getAwningList(String token);
	int addAwning(String token, AwningControl awningControl,AddModify addModify);
	AwningStatResult getAwningStat(String token, String deviceId);
	List getAwningLStatList(String token,HashMap<String,String> paramMap);
	int deleteAwningSeleted(String token,List<String> list);
	
}
