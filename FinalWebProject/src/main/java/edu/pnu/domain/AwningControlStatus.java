package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Entity
@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AwningControlStatus {
	@Column(length = 20,columnDefinition = "varchar(20) not null default 'normal'")
	String lightingCondition;
	@Column(length = 20,columnDefinition = "varchar(20) not null default 'normal'")
	String awningCondition;
	@Column(length = 20,columnDefinition = "varchar(20) not null default 'normal'")
	String batteryCondition;
	
	@Column(length = 100)
	String lightingMessage;
	@Column(length = 100)
	String awningMessage;
	@Column(length = 100)
	String batteryMessage;
	
	@Id
	@Column(length = 20,nullable = false)
	String awningDeviceId;
}
