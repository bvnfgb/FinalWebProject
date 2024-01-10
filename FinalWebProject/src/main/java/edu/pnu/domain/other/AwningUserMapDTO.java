package edu.pnu.domain.other;

import java.math.BigDecimal;

import edu.pnu.persistence.other.AwningUserMap;
import lombok.Data;
@Data
public class AwningUserMapDTO implements AwningUserMap{
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
}
