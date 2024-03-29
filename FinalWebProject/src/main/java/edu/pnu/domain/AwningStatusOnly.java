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
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter
public class AwningStatusOnly {
	
	//
	@Builder.Default
	@Column(columnDefinition = "varchar(20) default 'normal'")
	private String lightingCondition="normal";
	@Builder.Default
	@Column(columnDefinition = "varchar(20) default 'normal'")
	private String motorCondition="normal";
	@Builder.Default
	@Column(columnDefinition = "varchar(20) default 'normal'")
	private String batteryCondition="normal";
	@Builder.Default
	@Column(columnDefinition = "varchar(100) default 'null'")
	private String lightingMessage=null;
	@Column(columnDefinition = "varchar(100) default 'null'")
	@Builder.Default
	private String motorMessage=null;
	@Builder.Default
	@Column(columnDefinition = "varchar(100) default 'null'")
	private String batteryMessage=null;
	@Column(columnDefinition = "timestamp")
	private Timestamp lastReportedDate;
	@Column
	@Id
	
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
	@Column(unique = true
			,columnDefinition = "varchar(20) not null")
	
	private String deviceId;
	
	@Column(nullable = false,unique = true)
	private Integer controlId;
	
	public boolean isNormal() {
		if ((batteryCondition.compareTo("normal")==0)&&
			(lightingCondition.compareTo("normal")==0)&&
			(motorCondition.compareTo("normal")==0))
			return true;
		return false;
	}
	
}
