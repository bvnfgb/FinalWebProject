package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.EventHistory;

public interface EventHisRepository extends JpaRepository<EventHistory, Integer> {

}
