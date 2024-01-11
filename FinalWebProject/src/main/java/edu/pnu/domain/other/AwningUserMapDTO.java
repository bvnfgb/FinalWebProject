package edu.pnu.domain.other;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class AwningUserMapDTO {
	private String statusConnected;
    private String statusLighting;
    private BigDecimal statusTemperature;
    private String statusAwningExpand;
    private BigDecimal statusWindSpeed;
    private Integer statusBatteryCharge;
    private String managementNumber;
    private String installationLocationMemo;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer awningId;
    
    
    private String deviceId;
    private Integer controlId;
    private String awningOpenTimeLeft;
    private String awningOpenTimeRight;
    private String lightingCondition;
    private String motorCondition;
    private String batteryCondition;
    private String statusOperationMode;
}
