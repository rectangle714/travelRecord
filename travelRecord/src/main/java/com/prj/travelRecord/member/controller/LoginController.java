package com.prj.travelRecord.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prj.travelRecord.config.security.JwtTokenProvider;
import com.prj.travelRecord.domain.Member;
import com.prj.travelRecord.member.repository.MemberRepository;
import com.prj.travelRecord.member.service.MemberService;
import com.prj.travelRecord.member.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginController {

	
	private final JwtTokenProvider jwtTokenProvider;
	
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/sec")
	public ResponseEntity sec() {
		log.info("시큐리티 테스트");
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(value="/login", produces="application/json;charset=UTF-8")
	public String loginAction(@RequestBody Map<String, Object> requestData) throws IllegalArgumentException{
		
		Member member = memberRepository.findByLoginId(requestData.get("loginId").toString())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		
		String oriPw = requestData.get("loginPw").toString();
		
        if (!passwordEncoder.matches(requestData.get("loginPw").toString(), member.getLoginPw())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

		String token = null;
	
		token = jwtTokenProvider.createToken(member.getLoginId(), member.getRole());

		return token;
	}

	@PostMapping(value="/join", produces="application/json;charset=UTF-8")
	public Long join(@RequestBody MemberDTO requestData) {
		log.info("회원가입 시작");
		
		
		Member newMember = Member.createMember(requestData, passwordEncoder);
	  
		Long id = memberService.join(newMember); 
		
		return id;

	}

}
