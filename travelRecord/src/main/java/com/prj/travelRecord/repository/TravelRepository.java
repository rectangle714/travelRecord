package com.prj.travelRecord.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import com.prj.travelRecord.domain.TravelCode;
import com.prj.travelRecord.domain.TravelSpot;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelRepository {
	
	private final EntityManager em;

	@Transactional
	public void saveCode(TravelCode travelCode) {
		em.persist(travelCode);	
	}
	
	@Transactional
	public void saveSpot(TravelSpot travelSpot) {
		em.persist(travelSpot);	
		
	}

}
