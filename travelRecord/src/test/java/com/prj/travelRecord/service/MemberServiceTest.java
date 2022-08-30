package com.prj.travelRecord.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.prj.travelRecord.domain.Member;
import com.prj.travelRecord.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {

	
	@Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
	
    
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        member.setAgree(1);
        //when
        Long saveId = memberService.join(member);
        //then
        Member member1 = memberRepository.findOne(saveId);
        assertEquals(member.getId(), member1.getId());
    }

}
