package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ManageArea;
import java.util.List;


public interface ManageAreaRepository extends JpaRepository<ManageArea, Integer> {
	List<ManageArea> findByCity(String city);
}
