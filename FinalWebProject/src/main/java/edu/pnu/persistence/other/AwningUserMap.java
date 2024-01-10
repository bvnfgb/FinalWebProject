package edu.pnu.persistence.other;

import java.math.BigDecimal;

public interface AwningUserMap {//user/map의 요청필드만 커스텀하여 전송하는데 필요한 인터페이스
	
	String getStatusConnected();
	String getStatusLighting();
	BigDecimal getStatusTemperature();
	String getStatusAwningExpand();
	BigDecimal getStatusWindSpeed();
	Integer getStatusBatteryCharge();
	String getManagementNumber();
	String getInstallationLocationMemo();
	BigDecimal getLatitude();
	BigDecimal getLongitude();
	Integer getAwningId();
    
}
