package com.prj.travelRecord.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MainController {

	@PostMapping("main")
	public ResponseEntity test() {
		log.info("체크");
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
