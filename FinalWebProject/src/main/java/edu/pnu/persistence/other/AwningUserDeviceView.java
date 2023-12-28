package edu.pnu.persistence.other;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface AwningUserDeviceView {
	void setManagementArea1(String value);
	void setManagementArea2(String value);
	// Getter methods for transient fields
    String getManagementArea1();
    String getManagementArea2();

    // Getter methods for non-input transient fields
    String getLightingCondition();
    String getAwningCondition();
    String getBatteryCondition();

    

    // Getter methods for non-transient fields
    Integer getManagementArea();
    String getManagementNumber();
    String getDeviceId();
    String getInstallationLocationMemo();
    String getAwningOpenTimeLeft();
    String getAwningOpenTimeRight();
    Timestamp getAwningOpenScheduleTime();
    String getWindSpeedThreshold();
    String getStatusConnected();
    String getStatusLighting();
    String getStatusAwningExpand();
    BigDecimal getStatusTemperature();
    BigDecimal getStatusWindSpeed();
    Integer getStatusBatteryCharge();
    String getStatusOperationMode();
    Integer getAwningId();
}
