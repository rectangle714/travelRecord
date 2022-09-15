package com.prj.travelRecord.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prj.travelRecord.domain.TravelCode;
import com.prj.travelRecord.repository.TravelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelSpotController {
	
	private final TravelRepository travelRepository;

	  //장소 코드 불러오기 
	  @PostMapping("/spotCode")
	    public void spotCode(Model m) throws IOException, ParseException {
	        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=9rvlY4g%2BQNojvBPO73hCfQCO9R59a5jR6l57VYIWL7462sSGg5Ul4zLjG5d9GvFZKSgxjG0Bbu8RFMOWzB7SLA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TravelRecord", "UTF-8")); /*서비스명=어플명*/
	        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS (아이폰), AND (안드로이드),WIN (원도우폰), ETC*/
	        urlBuilder.append("&_type=json"); // json 형식으로 받아오기
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

	        String result = "";
	        result = sb.toString();
	        
	        JSONParser parser = new JSONParser();
	        Map<String,Object> map = new HashMap<>();
	        	        
	        try {
	           map = (Map<String, Object>) parser.parse( result );
	           map = (Map<String, Object>) parser.parse( map.get("response").toString() );
	           map = (Map<String, Object>) parser.parse( map.get("body").toString() );
	           map = (Map<String, Object>) parser.parse( map.get("items").toString() );
	        } catch (ParseException e) {
	           e.printStackTrace();
	        }
	        List<Map<String,Object>> list = new ArrayList<>();
	        JSONArray jsonArray = new JSONArray();
	        try {
	           jsonArray = (JSONArray) parser.parse(map.get("item").toString());
	        } catch (ParseException e) {
	           e.printStackTrace();
	        }
	        for (Object object : jsonArray) {
	           list.add((Map<String, Object>)object);
	        }
	        for (Map<String,Object> mapObj : list) {
	          int rnum = Integer.parseInt(mapObj.get("rnum").toString());
	          String code = mapObj.get("code").toString();
	          String name = mapObj.get("name").toString();
	          
	          TravelCode travelCode = new TravelCode();
	          travelCode.setRnum(rnum);
	          travelCode.setSpotCode(code);
	          travelCode.setSpotName(name);
	          
	          travelRepository.saveCode(travelCode);
	          
	        }                  	       
	    }
	  
	  @GetMapping("/spotInfo")
	  public ResponseEntity spotInfo(Model m, @RequestParam String areaCode, String sigunguCode, @RequestParam String cat1,
		                                  @RequestParam String contentTypeId, String cat2, @RequestParam String pageNo) throws IOException {
		        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList"); /*URL*/
		        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=9rvlY4g%2BQNojvBPO73hCfQCO9R59a5jR6l57VYIWL7462sSGg5Ul4zLjG5d9GvFZKSgxjG0Bbu8RFMOWzB7SLA%3D%3D"); /*Service Key*/
		        urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /*지역코드*/
		        urlBuilder.append("&" + URLEncoder.encode("sigunguCode", "UTF-8") + "=" + URLEncoder.encode(sigunguCode, "UTF-8")); /*시군구코드(areaCode 필수)*/
		        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", "UTF-8") + "=" + URLEncoder.encode(contentTypeId, "UTF-8")); /*관광타입(관광지, 숙박 등) ID*/
		        urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode(cat1, "UTF-8")); /*대분류 코드*/
		        urlBuilder.append("&" + URLEncoder.encode("cat2", "UTF-8") + "=" + URLEncoder.encode(cat2, "UTF-8")); /*중분류 코드(cat1필수)*/
		        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*현재 페이지 번호*/
		        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("TravelRecord", "UTF-8")); /*서비스명=어플명*/
		        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS (아이폰), AND (안드로이드),WIN (원도우폰), ETC*/
		        urlBuilder.append("&_type=json"); // json 형식으로 받아오기
		        URL url = new URL(urlBuilder.toString());
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		        conn.setRequestProperty("Content-type", "application/json");
		        BufferedReader rd;
		        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

		        m.addAttribute("spotInfo", sb.toString());					       
	       
		        log.info("장소 찾기 검색 성공");
				return new ResponseEntity(HttpStatus.OK);  
		    }
	  
	  
	  @GetMapping(value= "/keywordList" ,produces="application/json;charset=UTF-8")
	    public JSONArray TourAPIKeyword(Model m, @RequestParam String keyword) throws IOException {
	        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=9rvlY4g%2BQNojvBPO73hCfQCO9R59a5jR6l57VYIWL7462sSGg5Ul4zLjG5d9GvFZKSgxjG0Bbu8RFMOWzB7SLA%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("keyword", "UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*검색 요청할 키워드 (국문=인코딩 필요)*/
	        //urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*현재 페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
	        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS (아이폰), AND(안드로이드), ETC*/
	        urlBuilder.append("&_type=json"); // json 형식으로 받아오기
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

	        log.info("장소 검색 통신 성공 : "+keyword);
	        log.info(sb.toString());
	        
	        String result = sb.toString();

	        JSONParser parser = new JSONParser();
	        Map<String,Object> map = new HashMap<>();
	        
	        try {
				map = (Map<String, Object>) parser.parse( result );
				map = (Map<String, Object>) parser.parse( map.get("response").toString() );
			    map = (Map<String, Object>) parser.parse( map.get("body").toString() );
			    map = (Map<String, Object>) parser.parse( map.get("items").toString() );
			        
			} catch (ParseException e) {
				e.printStackTrace();
			}
	       
	        JSONArray jsonArray = new JSONArray();
	        try {
	           jsonArray = (JSONArray) parser.parse(map.get("item").toString());
	        } catch (ParseException e) {
	           e.printStackTrace();
	        }
	        
			return jsonArray;  
	    }

	  
}
