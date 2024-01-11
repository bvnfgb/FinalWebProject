package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AwningStatusOnly;
import java.util.List;


public interface AwningStatusRepository extends JpaRepository<AwningStatusOnly, Integer> {
	AwningStatusOnly findByDeviceId(String deviceId);
	int deleteAllByDeviceIdIn(List<String> list);
}
