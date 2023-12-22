package edu.pnu.service;

import org.springframework.http.ResponseEntity;

import edu.pnu.domain.AwningControl;

public interface AwningService {
	ResponseEntity<?> getAwningList(String token);
	ResponseEntity<?> addAwning(String token, AwningControl awningControl);
}
