package com.prj.travelRecord.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.prj.travelRecord.domain.TravelPicture;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TravelPictureRepository {

	private final EntityManager em;

	@Value("${spring.servlet.multipart.location}")
	private String fileDir;
	
	
	
	
	//=============================== DB 관련 =======================================//

	/**
	 * 여행사진 DB insert
	 * @param travelPicture
	 */
	public void insert(TravelPicture travelPicture) {
		em.persist(travelPicture);
	}

	/**
	 * 여행사진 DB delete
	 * @param id
	 */
	public void delete(long id) {
		// DB delete
	}

	public void update(){
	//서비스단에서 jpa로 조회 후 수정하면 영속성 컨텍스트가 자동으로 변경해준다고 하니 필요는 없는 것 같음?
	}

	/**
	 * 여행사진 id로 조회
	 * @param id
	 * @return
	 */
	public TravelPicture findById(long id) {
		return em.find(TravelPicture.class, id);
	}

	/**
	 * 여행일기 id로 조회
	 * @param diaryId
	 * @return 해당 여행일기의 여행사진 전체 리스트
	 */
	public List<TravelPicture> findByDiaryId(long diaryId) {
		return em.createQuery("select p from TravelPicture p where p.diary_id = :diaryId", TravelPicture.class)
				.setParameter("diary_id", diaryId)
				.getResultList();
	}

	/**
	 * dpYn N인 여행사진 조회 ( 여행일기 수정 시 사용 )
	 * @param diaryId
	 * @return 해당 여행일기의 여행사진 중 [dpYn = N]인 전체 리스트
	 */
	public List<TravelPicture> findNotDpFile(long diaryId) {
		return em.createQuery("select p from TravelPicture p where p.diary_id = :diaryId and p.dp_yn = 'N'", TravelPicture.class)
				.setParameter("diary_id", diaryId)
				.getResultList();
	}
	
	
	//================================ 첨부파일 관련 =======================================//
	
	/**
	 * 여행일기 이미지 서버에 이미지 저장 (단건)
	 * @param file
	 * @return 서버에 저장된 파일명
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String uplodDiaryImage(MultipartFile file) throws IllegalStateException, IOException {
		
		// 컨트롤러에서 체크하고 넘겨주지만 다른데서도 해당 로직 사용할 수 있다는 가정하에 한번더 validation
		if(file.isEmpty()) {
			return null;
		}
		// 이미지가 맞는지도 체크필요
		
		String realFileName = file.getOriginalFilename();
		String uploadFileName = createRandomFileName(realFileName);
		String fullPath = fileDir + uploadFileName;
		log.info("image Upload Start [ realFileName = '{}' ] [ uploadFileName = '{}' ]", realFileName, uploadFileName);
		log.info("image Upload fullPath = {}", fullPath);
		
		file.transferTo(new File(fullPath));
		
		return uploadFileName;
	}
	
	
	/**
	 * 여행일기 이미지 서버에 이미지 저장 (다건)
	 * @param files
	 * @return 서버에 저장된 파일명 리스트
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public List<String> uploadDiaryImages(List<MultipartFile> files) throws IllegalStateException, IOException {
		
		List<String> uploadFileList = new ArrayList<>();
		
		for( MultipartFile uploadFile : files ) {
			if( !uploadFile.isEmpty() ) {
				uploadFileList.add( uplodDiaryImage(uploadFile)) ;
			}
		}
		return uploadFileList;
	}
	
	
	/**
	 * 파일이름에서 파일 확장자 추출
	 * @param realFileName
	 * @return 해당 파일의 확장자명 ( . 빼고 ) ex: png
	 */
	public String getFileFormat(String realFileName){
		int subStrIndex = realFileName.lastIndexOf(".");
		return realFileName.substring(subStrIndex + 1 );
	}
	
	
	/**
	 * 서버에 저장될 난수의 파일이름 생성
	 * (파일이름 중복 방지)
	 * @param realFileName
	 * @return
	 */
	public String createRandomFileName(String realFileName) {
		return  UUID.randomUUID().toString() + "." + getFileFormat(realFileName);
	}
	
	
	/**
	 * 해당 여행일기에 속한 모든 이미지 삭제
	 * @param fileNames
	 */
	public void deleteImages(List<String> fileNames) {
		// DB삭제
		// 파일삭제
		for(String fileName : fileNames) {
			deleteImage(fileName);
		}
	}
	
	
	/**
	 * 서버에서 이미지 삭제 (단건)
	 * @param fileName
	 */
	public void deleteImage(String fileName /*추후엔 userId도 필요(/userId/fileName에 해당하는 이미지 삭제*/) {
		
		// 이렇게 하는게 맞을까....??????
		String filePath = fileDir + fileName;
		File file = new File(filePath);
		if( file.isFile() ) {
			file.delete();
		}
	}
	

}
