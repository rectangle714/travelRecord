package com.prj.travelRecord.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prj.travelRecord.domain.Member;
import  com.prj.travelRecord.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	@Transactional
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

}
