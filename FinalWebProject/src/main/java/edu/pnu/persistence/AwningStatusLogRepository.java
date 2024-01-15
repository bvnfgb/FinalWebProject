package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.AwningStatusLog;
import java.util.List;


public interface AwningStatusLogRepository extends JpaRepository<AwningStatusLog, Integer> {
	List<AwningStatusLog> findByDeviceId(String deviceId);
}
