package com.prj.travelRecord.travel_diary;

import java.io.IOException;

import com.prj.travelRecord.domain.TravelDiary;
import com.prj.travelRecord.repository.TravelDiaryRepository;
import com.prj.travelRecord.travel_diary.vo.TravelDiaryForm;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.prj.travelRecord.repository.TravelPictureRepository;
import com.prj.travelRecord.travel_diary.vo.ResultMap;
import com.prj.travelRecord.util.TrCommonMessage;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class TravelDiaryController {
	
	private final  TravelDiaryService travelDiaryService;

	// 테스트용 나중에 지우기
	private final TravelPictureRepository travelPictureRepository;

	/**
	 * @// TODO: 2022/09/18 전체적으로 http 응답 정리필요 -> 응답실패시 어떻게할지, 예외처리같은것들... 예외시 일괄로 반환하는 것 필요
	 */

	/**
	 * 여행일기 조회
	 * @param travelId
	 * @return
	 */
	@Operation(summary = "여행일기 조회", description = "여행 마스터 id로여행 일기를 조회합니다.")
	@GetMapping("/{travelId}")
	public ResponseEntity<ResultMap> getDiary(@PathVariable long travelId){
		TravelDiary diary = travelDiaryService.getDiary(travelId);

		ResultMap resultMap = new ResultMap(HttpStatus.OK, TrCommonMessage.SUCCESS, diary);
		return new ResponseEntity<>(resultMap,HttpStatus.OK);
	}

	/**
	 * 여행일기 저장
	 * @param travelDiaryForm
	 * @return
	 * @// TODO: 2022/09/18 적절한 예외처리 해주기
	 */
	@Operation(summary = "여행일기 저장", description = "여행일기를 저장합니다.")
	@PostMapping(value="/save"
				,consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultMap> save(@ModelAttribute TravelDiaryForm travelDiaryForm){

		// 적절한 예외처리해주기
		try {
			travelDiaryService.save(travelDiaryForm);

			ResultMap resultMap = new ResultMap(HttpStatus.OK, TrCommonMessage.SUCCESS, null);
			return new ResponseEntity<>(resultMap,HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	};

	/**
	 * 여행일기 수정
	 * @param travelDiaryForm
	 * @return
	 * @// TODO: 2022/09/18 적절한 예외처리 해주기
	 *
	 */
	@Operation(summary = "여행일기 수정", description = "여행일기를 수정합니다.")
	@PostMapping(value = "/update"
				,consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultMap update(@ModelAttribute TravelDiaryForm travelDiaryForm){
		// 적절한 예외처리해주기
		try {
			travelDiaryService.update(travelDiaryForm);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}


	/**
	 * 여행일기 삭제
	 * @param diaryId
	 * @return
	 * @// TODO: 2022/09/18  파라미터를 travelId로 지우는걸로 만들어야 할까?
	 */
	@Operation(summary = "여행일기 삭제", description = "여행일기를 삭제합니다.")
	@GetMapping("/delete/{diaryId}")
	public ResultMap delete (@PathVariable long diaryId){
		travelDiaryService.delete(diaryId);
		return null;
	}










	//==========================파일업로드 테스트용 나중에 지울것
	@PostMapping("/upload/image")
	public ResultMap uploadImage(@RequestParam MultipartFile file) throws IllegalStateException, IOException {

		//나중에 member 생기면 id 가져와서 폴더 만들어 주는 작업 필요
		/*
		 * String memberId = trUtils.getLoginId();
		 * 이런식으로 member정보 얻는 공통로직 만들고
		 * uplodDiaryImage는 파라미터로 memberId+ diaryId도 추가로 받아서 
		 * fullPath = fileDir + "/" + memberId + "/" + uploadFileName
		 * 으로 변경
		 * */
		
		// 이미지가 맞는지도 체크필요
		
		if(file.isEmpty()) {
			return new ResultMap(HttpStatus.BAD_REQUEST, TrCommonMessage.FAIL, "file is empty" );
		}
		
		travelPictureRepository.uplodDiaryImage(file);
		
		return new ResultMap(HttpStatus.OK, TrCommonMessage.SUCCESS,null );
		
		//예외처리 필요
	}



	@GetMapping("/delete/image/{fileName}")
	public ResultMap deleteImage(@PathVariable String fileName ) {
		/* 
		 * 1. diaryId로 여행일기에 포함된 파일 조회
		 * 2. 파일명 리스트로만듬 -> fileNames
		 * 3. 
		*/
		
//		List<String> fileNames = new ArrayList<>();
//		travelPictureRepository.deleteImages(fileNames);
		travelPictureRepository.deleteImage(fileName);

		return null;
	}
	//==========================파일업로드 테스트용 나중에 지울것
}
