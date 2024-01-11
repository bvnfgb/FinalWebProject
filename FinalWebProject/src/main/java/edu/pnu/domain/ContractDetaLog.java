package edu.pnu.domain;

import java.sql.Date;

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
public class ContractDetaLog {
	@Column(columnDefinition = "date ")
	private Date contractStartDate;

	@Column(columnDefinition = "date ")
	private Date contractTerminationDate;

	@Column(columnDefinition = "date default (current_date)")
	@Builder.Default
	private Date registrationDate=new Date(new java.util.Date().getTime());

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contractId;
	
	private Integer awningId;

	@Column(columnDefinition = "varchar(20) not null")
	private String awningDeviceId;
}
