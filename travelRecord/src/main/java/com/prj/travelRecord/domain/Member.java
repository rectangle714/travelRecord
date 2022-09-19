package com.prj.travelRecord.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prj.travelRecord.member.vo.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member implements UserDetails{
	
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
    @Enumerated(EnumType.STRING)
    private MemberRole role; //USER, ADMIN
    
    
    /*
     * spring security 관련
     */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/*
	 * 회원가입
	 */
	@Builder
    public Member(String loginId, String loginPw, String name, String nick, String mobile, String email, String birth
    		,Gender gender, MemberStatus memberStatus, MemberRole role, EntityInfo entityInfo, LocalDateTime lastLogin, int agree ) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.nick = nick;
        this.mobile = mobile;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
        this.memberStatus = memberStatus;
        this.role = role;
        this.entityInfo = entityInfo;
        this.lastLogin = lastLogin;
        this.agree = agree;
    }

    public static Member createMember(MemberDTO newMember, PasswordEncoder passwordEncoder) {
    	
    	EntityInfo entityInfo = new EntityInfo();
    	LocalDateTime localDateTime = LocalDateTime.now();
    	entityInfo.setRegisterId(newMember.getLoginId());
    	entityInfo.setUpdateId(newMember.getLoginId());
    	entityInfo.setRegisterDate(localDateTime);
    	entityInfo.setUpdateDate(localDateTime);
    	
        Member member = Member.builder()
                .loginId(newMember.getLoginId())
                .loginPw(passwordEncoder.encode(newMember.getLoginPw()))
                .name(newMember.getName())
                .nick(newMember.getNick())
                .mobile(newMember.getMobile())
                .email(newMember.getEmail())
                .birth(newMember.getBirth())
                .gender(newMember.getGender())
                .memberStatus(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .entityInfo(entityInfo)
                .lastLogin(localDateTime)
                .agree(newMember.getAgree())
                .build();
        
        return member;
    }
	 
    
    
}
