package edu.pnu.persistence;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ReservationDeta;

public interface ReservationDetaRepository extends JpaRepository<ReservationDeta, Timestamp> {

}
