package com.prj.travelRecord.diary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prj.travelRecord.diary.domain.Diary;

import lombok.RequiredArgsConstructor;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
