package edu.pnu.domain.other;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class AwningControl {
	String managementArea1;
	String managementArea2;
	BigDecimal longitude;
	BigDecimal latitude;
	Integer awningReopenTimeMinutes;
	String windSpeedThreshold;
	String awningOpenTimeRight;
	String awningOpenTimeLeft;
	String installationLocationMemo;
	Integer controlId;
	String deviceId;
	String managementNumber;
	Date finishDate;
	Date startDate;
	Integer awningId;
	
	String statusConnected;
	String statusLighting;
	BigDecimal statusTemperature;
	String statusAwningExpand;
	BigDecimal statusWindSpeed;
	Integer statusBatteryCharge;
}
