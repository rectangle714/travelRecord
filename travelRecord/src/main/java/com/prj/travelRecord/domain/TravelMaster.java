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
@Table(name="tr_master")
public class TravelMaster {
	@Id @GeneratedValue
    @Column(name="travel_id")
    private long id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;  // member-order 연관 관계 주인!
	@Column(name="local_code")
	private int localCode;
	@Column(name="start_date")
	private LocalDateTime startDate;
	@Column(name="end_date")
	private LocalDateTime endDate;
	@Enumerated(EnumType.STRING) //PUBLIC, PRIVATE
    @Column(name="travel_status")
	private TravelStatus travelStatus;
	@Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자
	
	
}
