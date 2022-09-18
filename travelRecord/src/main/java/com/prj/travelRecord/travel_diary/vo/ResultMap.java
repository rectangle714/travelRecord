package com.prj.travelRecord.travel_diary.vo;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMap {
	
	private HttpStatus status;
	
	private String result;
	
	private Object data;

	public ResultMap(HttpStatus status, String result, Object data) {
		this.status = status;
		this.result = result;
		this.data = data;
	}

	
}
