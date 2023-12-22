package edu.pnu.domain;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReservationDeta {
	@Column(nullable = false, length = 5)
	private String reservationItems;
	
	@Column(nullable = false, length = 10)
	private String reservationMethod;
	
	private Integer repeatDay;
	
	@Column(columnDefinition = "datetime")
	private Date startDate;
	
	@Column(columnDefinition = "datetime")
	private Date finshDate;
	
	@Column(length = 20,nullable = false)
	private String awningDeviceId;
	
	@Id
	@Column(nullable = false,columnDefinition = "timestamp default current_timestamp")
	private Timestamp registrationDate;
}
