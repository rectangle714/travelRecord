package com.prj.travelRecord.repository;

import com.prj.travelRecord.domain.TravelMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TravelMasterRepository {

    private final EntityManager em;


    public TravelMaster findById(long id){
        return em.find(TravelMaster.class, id);
    }

}
