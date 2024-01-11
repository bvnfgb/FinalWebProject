package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AwningLocationOnly;
import java.util.List;


public interface AwningLocationRepository extends JpaRepository<AwningLocationOnly, Integer> {
	AwningLocationOnly findByDeviceId(String deviceId);
	int deleteAllByDeviceIdIn(List<String> list);
}
