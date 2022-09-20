package com.prj.travelRecord.diary.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.boot.jaxb.hbm.spi.EntityInfo;

import lombok.Data;

@Entity
@Data
@Table(name="TR_DIARY")
public class Diary {

	@Id @GeneratedValue
    @Column(name="diary_id")
    private long id;
	@Column(name="travel_id")
	private String travelId;
	@Column(name="member_id")
	private String memberId;
	@Column(name="place_id")
	private String placeId;
	
	private String title;
    private String icon;
    private String weather;
    private String content;
    
    @Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자
	 
    
}
