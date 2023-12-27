package edu.pnu.service;

import java.util.List;

import edu.pnu.domain.AwningControl;
@SuppressWarnings("rawtypes")
public interface AwningService {
	
	List getAwningList(String token);
	int addAwning(String token, AwningControl awningControl);
	AwningStatResult getAwningStat(String token, String deviceId);
	List getAwningLStatList(String token);
	int deleteAwningSeleted(String token,List<String> list);
}
