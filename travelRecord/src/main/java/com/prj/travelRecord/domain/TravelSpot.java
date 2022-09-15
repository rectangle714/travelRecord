package com.prj.travelRecord.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TR_TRAVEL_SPOT")
public class TravelSpot {

	@Id
	@Column(name="content_id")
	private int contentId;
	private String addr1;
	private String addr2;
	@Column(name="area_code")
	private String areacode;
	@Column(name="book_tour")
	private String booktour;
	private String cat1;
	private String cat2;
	private String cat3;	
	@Column(name="content_type_id")
	private int contentTypeId;
	@Column(name="created_time")
	private Date createdTime;
	@Column(name="first_image")
	private String firstImage;
	@Column(name="first_image2")
	private String firstImage2;	
	@Column(precision = 20, scale = 15)
	private BigDecimal mapX;	
	@Column(precision = 20, scale = 15)
	private BigDecimal mapY;	
	private String mlevel;	
	@Column(name="modified_time")
	private Date modifiedTime;	
	@Column(name="read_count")
	private int readCount;	
	@Column(name="sigungu_code")
	private int sigunguCode;		
	private String tel;		
	private String title;		
	private int zipcode;		
	
}
