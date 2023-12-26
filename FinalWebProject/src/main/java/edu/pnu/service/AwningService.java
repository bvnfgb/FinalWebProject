package edu.pnu.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import edu.pnu.domain.AwningControl;

public interface AwningService {
	@SuppressWarnings("rawtypes")
	List getAwningList(String token);
	int addAwning(String token, AwningControl awningControl);
	AwningStatResult getAwningStat(String token, String deviceId);
}
