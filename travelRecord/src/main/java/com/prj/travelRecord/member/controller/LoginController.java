package com.prj.travelRecord.member.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {
	
	@GetMapping("/")
	public ResponseEntity root() {
		log.info("호출성공");
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@GetMapping("/sec")
	public ResponseEntity sec() {
		log.info("호출성공");
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/login")
    public ResponseEntity loginAction(@RequestBody Map<String, Object> requestData){
		log.info("호출성공");
		requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });
		return new ResponseEntity(HttpStatus.OK);
    }

	@PostMapping("/join")
    public ResponseEntity join(){
		return new ResponseEntity(HttpStatus.OK);
    }
	
}
