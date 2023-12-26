package edu.pnu.persistence.other;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public interface AwningIndividualStatus {// /device/view/{}용 커스텀 프로젝션 인터페이스
	//setter
	void setManagementArea1(String value);
	void setManagementArea2(String value);
	void setStartDate(Date value);
	void setFinshDate(Date value);
    void setLightingCondition(String value);
    void setAwningCondition(String value);
    void setBatteryCondition(String value);
    void setLightingMessage(String value);
    void setAwningMessage(String value);
    void setBatteryMessage(String value);
    
    
	//getter
    String getLightingCondition();
    String getAwningCondition();
    String getBatteryCondition();
    String getLightingMessage();
    String getAwningMessage();
    String getBatteryMessage();
	Date getStartDate();
	Date getFinshDate();
	String getManagementArea1();
	String getManagementArea2();
	String getManagementNumber();
    String getDeviceId();
    Integer getControlId();
    String getInstallationLocationMemo();
    String getAwningOpenTimeLeft();
    String getAwningOpenTimeRight();
    Timestamp getAwningOpenScheduleTime();
    String getWindSpeedThreshold();
    Integer getAwningReopenTimeMinutes();
    BigDecimal getLatitude();
    BigDecimal getLongitude();
    Timestamp getLastReportedDate();
    String getStatusConnected();
    String getStatusLighting();
    String getStatusAwningExpand();
    BigDecimal getStatusTemperature();
    BigDecimal getStatusWindSpeed();
    Integer getStatusBatteryCharge();
}
