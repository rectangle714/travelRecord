package com.prj.travelRecord.domain;

import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="tr_diary")
public class TravelDiary {
	@Id @GeneratedValue
    @Column(name="diary_id")
    private long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_id")
    private TravelMaster travelMaster; 
	
//	@OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name="picture_id")
//    private List<TravelPicture> TravelPicture;

	@Column(name="travel_date")
	private String travelDate;

	@Column(name="title")
	private String title;
	
	@Column(name="content")
	private String content;
	
	@Enumerated(EnumType.STRING) 
    @Column(name="feeling_icon")
	private FeelingIcon feelingIcon;
	
	@Enumerated(EnumType.STRING) 
    @Column(name="weather")
	private Weather weather;

	@Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자

}
