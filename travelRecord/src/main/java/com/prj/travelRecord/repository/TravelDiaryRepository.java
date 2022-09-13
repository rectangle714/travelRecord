package com.prj.travelRecord.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prj.travelRecord.domain.TravelDiary;

@Repository
public class TravelDiaryRepository {
	
	public String save(TravelDiary travelDiary) {
		// DB저장
		// 파일(사진,동영상) 저장
		return null;
	}
	
	public TravelDiary findById (String diaryId) {
		return null;
	}
	
	public List<TravelDiary> findByMember(String memberId){
		return null;
	}
	
	public void delete(String diaryId) {
		// DB삭제
		// 파일삭제
		
	}
	
	public void update(TravelDiary travelDiary) { // TravelDiary말고 updateFor따로 있어야 하나??? 어떻게해야하나?
		// DB수정
		// 파일수정
		
	}

}
