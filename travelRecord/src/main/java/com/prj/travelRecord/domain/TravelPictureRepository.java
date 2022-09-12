package com.prj.travelRecord.domain;

import org.springframework.stereotype.Repository;

@Repository
public class TravelPictureRepository {
	

	public String save(TravelPicture travelPicture) {
		// 파일(사진,동영상) 저장 후 파일 경로return(or 파일명)
		/**
		 * 한 폴더에 전체사진 넣는것은 무리인것 같고
		 * 멤버별로 폴더를 따서 넣어주는게 좋을 것 같음
		 * 마스터경로는 /picture같은걸로하고 그 밑에해당 멤버 id로 경로 없으면 새로 경로따고
		 * 있으면 해당 경로에 저장하고 파일명 리턴
		 * 파일 찾을 땐 /picture/멤버id/파일
		 */
		return "파일명";
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
