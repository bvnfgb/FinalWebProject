package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "awningDeviceId", referencedColumnName = "deviceId")
	private AwningControl awningControl;
	//일반 DB항목
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

	@Column(columnDefinition = "varchar(20) not null",insertable = false, updatable = false)
	private String awningDeviceId;

}
