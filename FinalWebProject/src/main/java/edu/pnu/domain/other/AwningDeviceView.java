package edu.pnu.domain.other;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class AwningDeviceView {
	private String lightingCondition;
    private String motorCondition;
    private String batteryCondition;
    private String lightingMessage;
    private String awningMessage;
    private String batteryMessage;
    private Date startDate;
    private Date finishDate;
    private String managementArea1;
    private String managementArea2;
    private String managementNumber;
    private String deviceId;
    private Integer controlId;
    private String installationLocationMemo;
    private String awningOpenTimeLeft;
    private String awningOpenTimeRight;
    private Timestamp awningOpenScheduleTime;
    private String windSpeedThreshold;
    private Integer awningReopenTimeMinutes;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Timestamp lastReportedDate;
    
    private String statusConnected;
    private String statusLighting;
    private String statusAwningExpand;
    private BigDecimal statusTemperature;
    private BigDecimal statusWindSpeed;
    private Integer statusBatteryCharge;
    private Integer awningId;
    private String statusOperationMode;
    private Integer company;
}
