package edu.pnu.domain;

import java.math.BigDecimal;
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
public class AwningControl {
	@Column(length = 20,nullable = false,unique = true)
	private String managementNumber;
	
	@Id
	@Column(length = 20)
	private String deviceId;
	
	@Column(nullable = false)
	private Integer controlId;
	
	@Column(length = 50,nullable = false,unique = true)
	private String installationLocationMemo;
	
	@Column(length = 10,nullable = false)
	private String awningOpenTimeLeft;
	
	@Column(length = 10,nullable = false)
	private String awningOpenTimeRight;
	
	@Column(length = 10,columnDefinition = "timastamp")
	private Timestamp awningOpenScheduleTime;
	
	@Column(length = 10,nullable = false)
	private String windSpeedThreshold;
	
	@Column(nullable = false)
	private Integer awingReopenTimeMinutes;
	
	@Column(nullable = false,columnDefinition = "decimal(10,7)")
	private BigDecimal latitude;
	
	@Column(nullable = false,columnDefinition = "decimal(10,7)")
	private BigDecimal longitude;
	
	@Column(columnDefinition = "timestamp")
	private Timestamp lastReportedDate;
	
	@Column(nullable = false)
	private Integer managementArea;
	
	@Column(nullable = false, columnDefinition = "varchar(10) default 'off'")
	private String statusConnected;
	
	@Column(nullable = false,columnDefinition = "varchar(10) default 'off'")
	private String statusLighting;
	
	@Column(nullable = false,columnDefinition = "varchar(10) default 'off'")
	private String statusAwningExpand;
	
	@Column(nullable = false,columnDefinition = "decimal(3,1) default '0.0' ")
	private BigDecimal statusTemperature;
	
	@Column(nullable = false,columnDefinition = "decimal(3,1) default '0.0'") 
	private BigDecimal statusWindSpeed;
	
	@Column(nullable = false,columnDefinition = "int default '0'")
	private Integer statusBatteryCharge;
}
