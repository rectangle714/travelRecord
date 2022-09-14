package com.prj.travelRecord.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prj.travelRecord.config.SecurityConfig;
import com.prj.travelRecord.config.security.JwtTokenProvider;
import com.prj.travelRecord.domain.Member;
import com.prj.travelRecord.member.service.MemberService;
import com.prj.travelRecord.member.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginController {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	private final MemberService memberService;
	

	@GetMapping("/sec")
	public ResponseEntity sec() {
		log.info("시큐리티 테스트");
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(value="/login", produces="application/json;charset=UTF-8")
	public String loginAction(@RequestBody Map<String, Object> requestData) {
		log.info("로그인 시작");
		String username = requestData.get("id").toString();
		List<String> roles = new ArrayList<>();
		roles.add("USER");
		String token = null;
	
		token = jwtTokenProvider.createToken(username, roles);

		log.info(token);

		return token;
	}

	@PostMapping(value="/join", produces="application/json;charset=UTF-8")
	public Long join(@RequestBody MemberDTO requestData) {
		log.info("회원가입 시작");
		PasswordEncoder enc = new BCryptPasswordEncoder();
		Member newMember = Member.createMember(requestData, SecurityConfig.passwordEncoder());
	  
		Long id = memberService.join(newMember); 
		
		return id;

	}

}
