package edu.pnu.forecast;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class WeatherLive {
	@Column(length = 10)
	String baseDate;
	@Column(length = 10)
	String baseTime;
	@Column(length = 10)
	String obsrValue;
	Integer nx;
	Integer ny;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer weatherId;
}
