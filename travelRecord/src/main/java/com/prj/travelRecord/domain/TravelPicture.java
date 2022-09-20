package com.prj.travelRecord.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="tr_picture")
public class TravelPicture {

	@Id @GeneratedValue
    @Column(name="picture_id")
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="diary_id")
    private TravelDiary travelDiary;

	// 실제 서버에 저장되는 uuid로된 파일명
	@Column(name="file_name")
	private String fileName;

	// 우리는 어플이니까 사진 다운로드는 필요 없을 것 같고 그렇다면 uploadName 컬럼은 필요 없을것 같
	// 고객이 업로드시 지정한 파일명
//	@Column(name="upload_name")
//	private String uploadName;
	
	@Column(name="dp_seq")
	private int dpSeq;

	@Column(name="dp_yn")
	private char dpYn;

	@Embedded
    private EntityInfo entityInfo; //등록일, 등록자, 수정일, 수정자

}
