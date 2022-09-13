package com.prj.travelRecord.repository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.prj.travelRecord.domain.TravelPicture;
import com.prj.travelRecord.travel_diary.vo.UploadFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TravelPictureRepository {
	

	@Value("${spring.servlet.multipart.location}")
	private String fileDir;
	
	public UploadFile uplodDiaryImage(MultipartFile file) throws IllegalStateException, IOException {
		
		// 컨트롤러에서 체크하고 넘겨주지만 다른데서도 해당 로직 사용할 수 있다는 가정하에 한번더 validation
		if(file.isEmpty()) {
			return null;
		}
		// 이미지가 맞는지도 체크필요
		
		String realFileName = file.getOriginalFilename();
		String uploadFileName = createRandomFileName(realFileName);
		String fullPath = fileDir + uploadFileName;
		log.info("image Upload Start [ realFileName = '{}' ] [ uploadFileName = '{}' ]", realFileName, uploadFileName);
		log.info("image Upload fullPath = {}", fullPath);
		
		file.transferTo(new File(fullPath));
		
		return new UploadFile(realFileName, uploadFileName);
	}
	
	public String getFileFormat(String realFileName){
		int subStrIndex = realFileName.lastIndexOf(".");
		return realFileName.substring(subStrIndex + 1 );
	}
	
	public String createRandomFileName(String realFileName) {
		return  UUID.randomUUID().toString() + "." + getFileFormat(realFileName);
	}
	
	public TravelPicture findById (String pictureId) {
		return null;
	}
	
	public void delete(String diaryId) {
		// DB삭제
		// 파일삭제
		
	}
	
	public void update(TravelPicture travelPicture) { // TravelDiary말고 updateFor따로 있어야 하나??? 어떻게해야하나?
		// DB수정
		// 파일수정
	}

}
