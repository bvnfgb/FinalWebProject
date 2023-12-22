package edu.pnu.domain;

import java.sql.Timestamp;

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
public class EventHistory {
	@Id
	private Long eventId;
	
	@Column(nullable = false,length = 20)
	private String manageNum;
	
	@Column(nullable = false)
	private Integer contId;
	
	@Column(nullable = false,length = 50)
	private String installLocaMemo;
	
	@Column(nullable = false,columnDefinition = "timestamp default current_timestamp")
	private Timestamp eventDate;
	
	@Column(nullable = false)
	private Integer manageArea;
}
