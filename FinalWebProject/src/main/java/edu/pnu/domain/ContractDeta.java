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
public class ContractDeta {
	// 연관관계를 위한 엔티티
	
	//일반 DB항목
	@Column(columnDefinition = "date ")
	private Date contractStartDate;

	@Column(columnDefinition = "date ")
	private Date contractTerminationDate;

	@Column(columnDefinition = "date default (current_date)")
	private Date registrationDate;

	@Id
	
	private Integer awningId;

	@Column(columnDefinition = "varchar(20) not null")
	private String awningDeviceId;

}
