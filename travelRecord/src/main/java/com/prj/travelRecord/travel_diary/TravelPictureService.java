package com.prj.travelRecord.travel_diary;

import com.prj.travelRecord.domain.TravelDiary;
import com.prj.travelRecord.domain.TravelPicture;
import com.prj.travelRecord.repository.TravelPictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@ TODO: 2022/09/18 이 클래스 이름이 service가 맞는걸까? pictureUtil??
@Service
@RequiredArgsConstructor
public class TravelPictureService {

    public final TravelPictureRepository travelPictureRepository;

    //---------- DB 관련 -------------

    /**
     * TravelPicture DB insert
     * @param fileName
     * @param dpSeq
     * @param dpYn
     * @param travelDiary
     *
     * @// TODO: 2022/09/18 파라미터 이렇게 많이 받지 말고 dto로 받던지 아님 dpYn,dpVersion은 insert시 기본 값이 있으니 fileName,Seq,Diary을 파라미터로 받는 생성자 만들던지
     */
    public void save(String fileName, int dpSeq, char dpYn, TravelDiary travelDiary){
        // 생성자든 메소드든 만들어주기
        TravelPicture travelPicture = new TravelPicture();
        travelPicture.setFileName(fileName);
        travelPicture.setDpSeq(dpSeq);
        travelPicture.setDpYn(dpYn);
        travelPicture.setTravelDiary(travelDiary);

        travelPictureRepository.insert(travelPicture);
    }

    /**
     * 기존 여행일기에 있던 여행사진 dpYn 'N'으로 update
     * @// TODO: 2022/09/18   Y로 업데이트 할일은 없을것이라 그냥 N으로 박았는데 혹시 Y로 업데이트 필요하게되면 파라미터로 dpYn 넘겨주기
     * @param diaryId
     */
    public void updateDpYnN(long diaryId){
        List<TravelPicture> travelPicures = travelPictureRepository.findByDiaryId(diaryId);

        for (TravelPicture travelPicure : travelPicures) {
            travelPicure.setDpYn('N');
            //이렇게되면 jpa 영속성 컨텍스트가 자동으로 변경해준다는고 같은뎀....???
        }

    }

    /**
     * 여행일기에 속한 여행사진 전체 삭제
     * @param diaryId
     */
    public void delete(long diaryId){
        travelPictureRepository.delete(diaryId);
        deleteFiles(diaryId, 'A');
    }

    //---------- 첨부파일 관련 -------------

    /**
     * 서버에 파일 저장
     * @param file
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IllegalStateException, IOException {

        //나중에 member 생기면 id 가져와서 폴더 만들어 주는 작업 필요
        /*
         * String memberId = trUtils.getLoginId();
         * 이런식으로 member정보 얻는 공통로직 만들고
         * uplodDiaryImage는 파라미터로 memberId+ diaryId도 추가로 받아서
         * fullPath = fileDir + "/" + memberId + "/" + uploadFileName
         * 으로 변경
         * */

        // 이미지가 맞는지도 체크필요
        if(file.isEmpty()) {
            return null;
        }

        String uploadFileName = travelPictureRepository.uplodDiaryImage(file);

        return uploadFileName;
        //예외처리 필요
    }

    /**
     * 서버 파일 삭제
     * @param diaryId   여행일기id
     * @param dpYn      N : dpYn=N인 파일만 삭제 / A : 전체삭제
     */
    public void deleteFiles( long diaryId, char dpYn ) {
        List<String> fileNames = getFileNames(diaryId, dpYn);
        travelPictureRepository.deleteImages(fileNames);
    }

    /**
     * 해당 여행일기에 포함되있는 파일명 조회
     * @param diaryId 여행일기id
     * @return
     */
    private List<String> getFileNames(long diaryId, char dpYn) {
        List<String> fileNames = new ArrayList<>();
        List<TravelPicture> travelPictures = new ArrayList<>();

        if(dpYn =='N'){ //update 시 기존 N이었던 파일만 삭제
            travelPictures = travelPictureRepository.findNotDpFile(diaryId);
        }
        travelPictures = travelPictureRepository.findByDiaryId(diaryId);

        for (TravelPicture travelPicture : travelPictures) {
            fileNames.add(travelPicture.getFileName());
        }
        return fileNames;
    }
}
