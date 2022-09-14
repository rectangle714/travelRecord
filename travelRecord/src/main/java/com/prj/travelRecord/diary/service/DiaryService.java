package com.prj.travelRecord.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.travelRecord.diary.domain.Diary;
import com.prj.travelRecord.diary.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;
	
	public List<Diary> diaryList(){
		List<Diary> list = diaryRepository.findAll();
		return list;
	}
		
}
