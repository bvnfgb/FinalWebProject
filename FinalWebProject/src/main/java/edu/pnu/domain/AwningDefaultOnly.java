package edu.pnu.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter
public class AwningDefaultOnly {
	//연관관계를 위한 객체
	
	//
	@Column(columnDefinition = "awning_id")
	@Id
	
	private Integer awningId;
	
	@Column(length = 10,nullable = false)
	private String awningOpenTimeLeft;
	
	@Column(length = 10,nullable = false)
	private String awningOpenTimeRight;
	
	@Column(length = 10,columnDefinition = "timastamp")
	private Timestamp awningOpenScheduleTime;
	
	@Column(length = 10,nullable = false)
	private String windSpeedThreshold;
	
	@Column(nullable = false)
	private Integer awningReopenTimeMinutes;
	
	@Column(unique = true
			,columnDefinition = "varchar(20) not null")
	
	private String deviceId;
	
	@Column(nullable = false,unique = true)
	private Integer controlId;
}
