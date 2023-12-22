package edu.pnu.domain;

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
public class Event {
	@Column(nullable = false)
	private Long eventId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eventRealId;
	
	@Column(length = 10)
	private String eventType;
	
	@Column(length = 10)
	private String Type;
	
	@Column(length = 10)
	private String EventType2;
	
	@Column(length = 20)
	private String EventValue;
}
