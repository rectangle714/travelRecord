package com.prj.travelRecord.travel_diary.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {
	
	private String realFileName;
	
	private String uploadFileName;

	public UploadFile(String realFileName, String uploadFileName) {
		this.realFileName	= realFileName;
		this.uploadFileName	= uploadFileName;
	}

	@Override
	public String toString() {
		return "UploadFile [realFileName=" + realFileName + ", uploadFileName=" + uploadFileName + "]";
	}
	
	

}
