package edu.pnu.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Event {
	@Id
	private Integer awningId;
	
	@Column(length = 10)
	private String eventType;//제어변경수신 (변경)있음/없음
	
	@Column(length = 10)
	private String type;//보냄/받음
	
	@Column(length = 10)
	private String eventType2;//배터리상태 정상/경고
	
	@Column(length = 10)
	private String eventType3;//모터상태 정상/경고
	
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	@Column(columnDefinition = "timestamp")
	private Date reportedTime=new Date();
}
