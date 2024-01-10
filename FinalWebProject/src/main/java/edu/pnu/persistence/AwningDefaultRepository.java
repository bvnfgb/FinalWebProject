package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AwningDefaultOnly;
import java.util.List;


public interface AwningDefaultRepository extends JpaRepository<AwningDefaultOnly, Integer> {
	AwningDefaultOnly findByDeviceId(String deviceId);

	AwningDefaultOnly findFirstByOrderByAwningIdDesc();
	 
}
