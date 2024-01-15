package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
