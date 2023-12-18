package edu.pnu.Forecast;

import lombok.Data;
@Data
public class ForecastData {
    private String fcstValue;
    private String fcstDate;
    private String category;
    private String baseTime;

    // 생성자, 게터, 세터 등 필요한 메서드를 추가할 수 있음
    
    // 예시로 toString 메서드 추가
    @Override
    public String toString() {
        return "ForecastData{" +
                "fcstValue='" + fcstValue + '\'' +
                ", fcstDate='" + fcstDate + '\'' +
                ", category='" + category + '\'' +
                ", baseTime='" + baseTime + '\'' +
                '}';
    }
}