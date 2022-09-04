package com.prj.travelRecord.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.prj.travelRecord.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager em;

	public void save(Member member) {
		em.persist(member);
	}
	
	public Member findOne(Long id){
        return em.find(Member.class, id);
    }

}
