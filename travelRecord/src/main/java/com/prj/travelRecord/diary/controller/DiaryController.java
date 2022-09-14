package com.prj.travelRecord.diary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.travelRecord.diary.domain.Diary;
import com.prj.travelRecord.diary.service.DiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
	
	private final DiaryService diaryService;

	//다이어리 목록 조회
	@PostMapping("/list")
	public List<Diary> diaryList(Diary diary) {
		List<Diary> diaryList = diaryService.diaryList();
		return diaryList;
	}
	
}
