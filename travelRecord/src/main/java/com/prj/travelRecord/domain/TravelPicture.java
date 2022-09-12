package com.prj.travelRecord.domain;

import java.time.LocalDateTime;
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

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelPicture {
	
	@Id @GeneratedValue
    @Column(name="picture_id")
    private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="diary_id")
    private TravelDiary travelDiary;
	
	@Column(name="dp_seq")
	private int dpSeq;
	
	// 우리는 어플이니까 사진 다운로드는 필요 없을 것 같고 그렇다면 uploadName 컬럼은 필요 없을것 같
	// 고객이 업로드시 지정한 파일명
//	@Column(name="upload_name")
//	private String uploadNamd;
	
	// 실제 서버에 저장되는 uuid로된 파일명
	@Column(name="save_name")
	private String saveName;
	
	//얘넨 실제로 DB에 넣을 것은 아닌데 entity에 넣는게 아닌가??? 찾아보기
//	private List<MultipartFile> imageFiles;
//	private MultipartFile attachFile;
	
	@Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자

}
