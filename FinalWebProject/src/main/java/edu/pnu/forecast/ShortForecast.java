package edu.pnu.Forecast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShortForecast {
	public void test1() throws Exception {
		
	
	StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=QC3IqYk7CfC8vM6GqkDj1gYFbZdJbl1vNV8ly8VR7G558o8KIvXJ0vQAzh4G4RtJIxWgcafouF%2BJIejePzKthw%3D%3D"); /*Service Key*/
    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
    urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
    urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20231217", "UTF-8")); /*‘21년 6월 28일발표*/
    urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("2300", "UTF-8")); /*05시 발표*/
    urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
    urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
    
    
    URL url = new URL(urlBuilder.toString());
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
    System.out.println("Response code: " + conn.getResponseCode());
    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
        sb.append(line);
    }
    rd.close();
    conn.disconnect();
//    System.out.println(sb.toString());
    String jsonString = sb.toString();

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(jsonString);

    // 원하는 필드를 찾기
    JsonNode itemsNode = jsonNode.path("response").path("body").path("items").path("item");

    // 모든 item을 순회하며 fcstDate 값을 출력
    List<ForecastData> forecastDataList = new ArrayList<>();

    // 각 item에서 필요한 데이터를 읽어서 ForecastData 객체에 저장 후 리스트에 추가
    for (JsonNode itemNode : itemsNode) {
        ForecastData forecastData = new ForecastData();
        forecastData.setFcstValue(itemNode.path("fcstValue").asText());
        forecastData.setFcstDate(itemNode.path("fcstDate").asText());
        forecastData.setCategory(itemNode.path("category").asText());
        forecastData.setBaseTime(itemNode.path("baseTime").asText());

        forecastDataList.add(forecastData);
    }
    String today="20231218";
    // 리스트에 저장된 데이터 출력 (예시)
    for (ForecastData data : forecastDataList) {
    	if(data.getFcstDate().equals(today)&&(data.getCategory().equals("UUU") ||data.getCategory().equals("VVV") )) {
    		if(Math.abs(Double.parseDouble((data.getFcstValue())))>=1)
    			System.out.println(data);
    	}
    		
        
    }
	}
	
}
