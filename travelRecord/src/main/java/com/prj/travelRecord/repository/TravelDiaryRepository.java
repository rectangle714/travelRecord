package com.prj.travelRecord.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.prj.travelRecord.domain.TravelDiary;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TravelDiaryRepository {

	private final EntityManager em;
	
	public void insert(TravelDiary travelDiary) {
		em.persist(travelDiary);
	}
	
	public TravelDiary findById ( long id) {
		return em.find(TravelDiary.class, id);
	}

	public TravelDiary findByTravelId(long travelId){
		return em.createQuery("select d from TravelDiary d where d.travel_id", TravelDiary.class)
				.setParameter("trvel_id", travelId)
				.getSingleResult();
	}
	
	public void delete(long diaryId) {
		// DB삭제
		// 파일삭제
		
	}
	
	public void update(TravelDiary travelDiary) { // TravelDiary말고 updateFor따로 있어야 하나??? 어떻게해야하나?
		// DB수정
	}

}
