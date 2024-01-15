package edu.pnu.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class AwningStatusLog {
	@Builder.Default
	@Column(columnDefinition = "varchar(20) default NULL")
	private String lightingCondition="normal";
	@Builder.Default
	@Column(columnDefinition = "varchar(20) default NULL")
	private String motorCondition="normal";
	@Builder.Default
	@Column(columnDefinition = "varchar(20) default NULL")
	private String batteryCondition="normal";
	@Builder.Default
	@Column(columnDefinition = "varchar(100) default NULL")
	private String lightingMessage=null;
	@Column(columnDefinition = "varchar(100) default NULL")
	@Builder.Default
	private String motorMessage=null;
	@Builder.Default
	@Column(columnDefinition = "varchar(100) default NULL")
	private String batteryMessage=null;
	@Column(columnDefinition = "timestamp")
	@Builder.Default
	private Timestamp reportedDate=new Timestamp(System.currentTimeMillis());
	@Column(nullable = false)
	
	private Integer awningId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logId;
	
	@Column( columnDefinition = "varchar(10) default NULL")
	@Builder.Default
	private String statusConnected="off";
	
	@Column(columnDefinition = "varchar(10) default NULL")
	@Builder.Default
	private String statusLighting="off";
	
	@Column(columnDefinition = "varchar(10) default NULL")
	@Builder.Default
	private String statusAwningExpand="off";
	
	@Column(columnDefinition = "decimal(4,1) default NULL ")
	@Builder.Default
	private BigDecimal statusTemperature=new BigDecimal(0.0);
	
	@Column(columnDefinition = "decimal(3,1) default null") 
	@Builder.Default
	private BigDecimal statusWindSpeed=new BigDecimal(0.0);
	
	@Column(columnDefinition = "int default null")
	@Builder.Default
	private Integer statusBatteryCharge=0;
	@Column(columnDefinition = "varchar(20) not null")
	
	private String deviceId;
	
	@Column(nullable = false)
	private Integer controlId;
}
