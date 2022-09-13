package com.prj.travelRecord.travel_diary.vo;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMap {
	
	private HttpStatus status;
	
	private String result;
	
	private String data;

	public ResultMap(HttpStatus status, String result, String data) {
		this.status = status;
		this.result = result;
		this.data = data;
	}

	
}
