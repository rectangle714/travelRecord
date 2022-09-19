package com.prj.travelRecord.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prj.travelRecord.domain.TravelCode;
import com.prj.travelRecord.domain.TravelSpot;
import com.prj.travelRecord.repository.TravelRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelSpotService {
	
	private final TravelRepository TravelRepository;

	@Transactional
    public String saveCode(TravelCode travelCode){
		TravelRepository.saveCode(travelCode);
        return travelCode.getSpotCode();
    }

	@Transactional
    public String saveSpot(TravelSpot travelspot){
		TravelRepository.saveSpot(travelspot);
        return travelspot.getTitle();
    }
  
	 
}
