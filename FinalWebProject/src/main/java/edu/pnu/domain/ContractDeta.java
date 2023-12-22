package edu.pnu.domain;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ContractDeta {
	@Column(columnDefinition = "date default (current_date)")
	private Date contractStartDate;
	
	@Column(columnDefinition = "date ")
	private Date contractTerminationDate;
	
	@Column(columnDefinition = "date ")
	private Date registrationDate;
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contractId;
	
	@Column(columnDefinition = "varchar(20) not null")
	private String awningDeviceId;
}
