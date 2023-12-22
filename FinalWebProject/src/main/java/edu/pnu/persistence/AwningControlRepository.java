package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.AwningControl;
import edu.pnu.jwt.persistence.AwningUserMap;

public interface AwningControlRepository extends JpaRepository<AwningControl, String> {
	@Query("SELECT a FROM AwningControl a")
	List<AwningUserMap> findAllByUserMap();
}
