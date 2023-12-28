package edu.pnu.persistence.other;

public interface AwningUserMap {//user/map의 요청필드만 커스텀하여 전송하는데 필요한 인터페이스
	
	String getStatusConnected();
	String getStatusLighting();
	Double getStatusTemperature();
	String getStatusAwningExpand();
	Double getStatusWindSpeed();
	Integer getStatusBatteryCharge();
	String getManagementNumber();
	String getInstallationLocationMemo();
	Double getLatitude();
	Double getLongitude();
	String getAwningId();
    
}
