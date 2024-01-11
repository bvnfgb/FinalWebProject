package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ContractDeta;


public interface ContractDetaRepository extends JpaRepository<ContractDeta, Integer> {
	ContractDeta findByAwningDeviceId(String awningDeviceId);
	int deleteAllByAwningDeviceIdIn(List<String> list);
}
