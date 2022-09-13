package com.prj.travelRecord.travel_diary;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prj.travelRecord.repository.TravelPictureRepository;
import com.prj.travelRecord.travel_diary.vo.ResultMap;
import com.prj.travelRecord.travel_diary.vo.UploadFile;
import com.prj.travelRecord.util.TrCommonMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class TravelDiaryController {
	
	private final TravelPictureRepository travelPictureRepository;
	
	@PostMapping("/upload/image")
	public ResultMap uploadImage(@RequestParam MultipartFile file) throws IllegalStateException, IOException {

		//나중에 member 생기면 id 가져와서 폴더 만들어 주는 작업 필요
		/*
		 * String memberId = trUtils.getLoginId();
		 * 이런식으로 member정보 얻는 공통로직 만들고
		 * uplodDiaryImage는 파라미터로 memberId도 추가로 받아서 
		 * fullPath = fileDir + "/" + memberId + "/" + uploadFileName
		 * 으로 변경
		 * */
		
		// 이미지가 맞는지도 체크필요
		
		if(file.isEmpty()) {
			return new ResultMap(HttpStatus.BAD_REQUEST, TrCommonMessage.FAIL, "file is empty" );
		}
		
		UploadFile uploadFile = travelPictureRepository.uplodDiaryImage(file);
		
		return new ResultMap(HttpStatus.OK, TrCommonMessage.SUCCESS, uploadFile.toString() );
		
		//예외처리 필요
	}
}
