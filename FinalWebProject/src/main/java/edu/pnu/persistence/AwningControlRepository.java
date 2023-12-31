package edu.pnu.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pnu.domain.AwningControl;
import edu.pnu.persistence.other.AwningIndividualStatus;
import edu.pnu.persistence.other.AwningUserDeviceView;
import edu.pnu.persistence.other.AwningUserMap;

public interface AwningControlRepository extends JpaRepository<AwningControl, String> {
	@Query("SELECT a FROM AwningControl a")
	List<AwningUserMap> findAllByUserMap();
	
	@Query("SELECT a FROM AwningControl a WHERE a.deviceId = :deviceId")
	AwningIndividualStatus findByIndvidualStatus(@Param("deviceId") String deviceId);

	@Query("SELECT a FROM AwningControl a")
	List<AwningUserDeviceView> findAllByUserDevice();
	
	AwningControl findByAwningId(Integer awningId);
	
	Optional<AwningControl> findByDeviceId(String deviceId);
	int deleteAllByDeviceIdIn(List<String> deviceIds);
	
}
