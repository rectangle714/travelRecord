package com.prj.travelRecord.member.vo;

import java.time.LocalDateTime;

import com.prj.travelRecord.domain.EntityInfo;
import com.prj.travelRecord.domain.Gender;
import com.prj.travelRecord.domain.MemberRole;
import com.prj.travelRecord.domain.MemberStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberDTO {
	
	private long id;
	private String loginId;
	private String loginPw;
    private String name;
    private String nick;
    private String mobile;
    private String email;
    private String birth;
    private Gender gender; //FEMALE, MALE
    private MemberStatus memberStatus;
    private int agree;
    private LocalDateTime lastLogin;
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자
    private MemberRole role; 
	
}
