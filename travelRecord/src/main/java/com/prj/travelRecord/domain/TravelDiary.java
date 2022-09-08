package com.prj.travelRecord.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TRAVEL_DIARY")
public class TravelDiary {
	@Id @GeneratedValue
    @Column(name="diary_id")
    private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_id")
    private TravelMaster travelMaster; 
	
	@Column(name="title")
	private String title;
	
	@Enumerated(EnumType.STRING) 
    @Column(name="feelingIcon")
	private FeelingIcon feelingIcon;
	
	@Enumerated(EnumType.STRING) 
    @Column(name="weather")
	private Weather weather;
	
	@Column(name="travelDate")
	private String travelDate;
	
	// 내용 어떻게 할지 생각해서 추가 -> html 그냥 db에 저장...? ** 그냥 자리 정해서 사진, 텍스트 받기로 함
	
	@Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자

}
