package com.prj.travelRecord.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TR_MEMBER")
public class Member {
	
	@Id @GeneratedValue
    @Column(name="memeber_id")
    private long id;
	@Column(name="login_id")
	private String loginId;
	@Column(name="login_pw")
	private String loginPw;
    private String name;
    private String nick;
    private String mobile;
    private String email;
    private String birth;
    @Enumerated(EnumType.STRING)
    private Gender gender; //FEMALE, MALE
    @Enumerated(EnumType.STRING) //ACTIVE, INACTIVE, EXPIRED
    @Column(name="member_status")
    private MemberStatus memberStatus;
    private int agree;
    @Column(name="last_login")
    private LocalDateTime lastLogin;
    @Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자
    @OneToMany(mappedBy = "member") // mappedBy 연관관계 주인이 아닐 때 적어줌 private
    List<TravelMaster> travelMaster = new ArrayList<>();
	 
    
    
}