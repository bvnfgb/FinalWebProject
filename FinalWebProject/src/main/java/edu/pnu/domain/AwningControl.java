package edu.pnu.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class AwningControl {
	//DB에 저장되지않는 임시항목 2개
	@Transient
	private String managementArea1;
	@Transient
	private String managementArea2;
	//다른 DB에 저장되지만 입력을 받지않는 항목 6개
	@Transient
	final String lightingCondition="normal";
	@Transient
	final String awningCondition="normal";
	@Transient
	final String batteryCondition="normal";
	@Transient
	final String lightingMessage=null;
	@Transient
	final String awningMessage=null;
	@Transient
	final String batteryMessage=null;
	//다른 DB에 저장되는 임시항목 2개
	@Transient
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date startDate;
	@Transient
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date finshDate;
	//이하 DB항목
	@Column(length = 20,nullable = false)
	private String managementNumber;
	
	@Column(length = 20,nullable = false,unique = true)
	private String deviceId;
	
	@Column(nullable = false,unique = true)
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
	private Integer awningReopenTimeMinutes;
	
	@Column(nullable = false,columnDefinition = "decimal(10,7)")
	private BigDecimal latitude;
	
	@Column(nullable = false,columnDefinition = "decimal(10,7)")
	private BigDecimal longitude;
	
	@Column(columnDefinition = "timestamp")
	private Timestamp lastReportedDate;
	
	@Column
	private Integer managementArea;
	
	
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer awningId;
	
	@Column(nullable = false, columnDefinition = "varchar(10) default 'off'")
	@Builder.Default
	private String statusConnected="off";
	
	@Column(nullable = false,columnDefinition = "varchar(10) default 'off'")
	@Builder.Default
	private String statusLighting="off";
	
	@Column(nullable = false,columnDefinition = "varchar(10) default 'off'")
	@Builder.Default
	private String statusAwningExpand="off";
	
	@Column(nullable = false,columnDefinition = "decimal(4,1) default '0.0' ")
	@Builder.Default
	private BigDecimal statusTemperature=new BigDecimal(0.0);
	
	@Column(nullable = false,columnDefinition = "decimal(3,1) default '0.0'") 
	@Builder.Default
	private BigDecimal statusWindSpeed=new BigDecimal(0.0);
	
	@Column(nullable = false,columnDefinition = "int default '0'")
	@Builder.Default
	private Integer statusBatteryCharge=0;
	
	@Column(nullable = false,columnDefinition = "varchar(10) default 'auto'")
	@Builder.Default
	private String statusOperationMode="auto";
}
