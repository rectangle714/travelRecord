package com.prj.travelRecord.travel_diary;

import com.prj.travelRecord.domain.TravelDiary;
import com.prj.travelRecord.repository.TravelDiaryRepository;
import com.prj.travelRecord.repository.TravelMasterRepository;
import com.prj.travelRecord.travel_diary.vo.TravelDiaryForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelDiaryService {

    private final TravelDiaryRepository travelDiaryRepository;
    private final TravelMasterRepository travelMasterRepository;

    private final TravelPictureService travelPictureService;

    public TravelDiary getDiary(long travelId){
        return travelDiaryRepository.findByTravelId(travelId);
    }

    /**
     * 여행일기 저장
     *
     * @param travelDiaryForm
     * @throws IOException
     * @// TODO: 2022/09/18 생성자,생성일
     * 
     */
    public void save(TravelDiaryForm travelDiaryForm) throws IOException {
        // 여행일기 insert
        TravelDiary travelDiary = setTravelDiary(travelDiaryForm);
        travelDiaryRepository.insert(travelDiary);
        // 여행사진 insert
        ArrayList<MultipartFile> attachFiles = travelDiaryForm.getAttachFiles();
        if( attachFiles != null && attachFiles.size() > 0) {
        	saveAttachFiles(travelDiary, attachFiles);
        }
        
    }

    /**
     * 여행일기 수정
     *
     * @param travelDiaryForm
     * @throws IOException
     * @// TODO: 2022/09/18 수정자,수정일
     */
    public void update(TravelDiaryForm travelDiaryForm) throws IOException {
        // 여행일기 DB update
        TravelDiary travelDiary = setTravelDiary(travelDiaryForm);
        travelDiaryRepository.update(travelDiary);

        // 여행사진 update start
        long diaryId = travelDiary.getId();
        // 원래있던 파일 가져와서 dpYn = N 으로 update
        travelPictureService.updateDpYnN(diaryId);

        // 새로운 파일 insert
        ArrayList<MultipartFile> attachFiles = travelDiaryForm.getAttachFiles();
        saveAttachFiles(travelDiary, attachFiles);

        // 새로운파일 insert 성공하면 원래 있던 파일 delete
        travelPictureService.deleteFiles(diaryId, 'N');
        // 여행사진 update end
    }

    /**
     * 여행일기 삭제
     * @param diaryId
     */
    public void delete(long diaryId) {
        travelDiaryRepository.delete(diaryId);
        travelPictureService.delete(diaryId);
    }

    /**
     * 첨부파일 저장(여행일기 사진 저장)
     *
     * @param travelDiary
     * @param attachFiles
     * @throws IOException
     */
    private void saveAttachFiles(TravelDiary travelDiary, ArrayList<MultipartFile> attachFiles) throws IOException {
        for (MultipartFile file : attachFiles) {
            // 서버에 파일저장
            String fileName = travelPictureService.uploadFile(file);
            // 서버에 파일 저장되면 DB에 insert
            if (fileName != null) {
                int dpSeq = attachFiles.indexOf(file);
                // 처음 insert 할 떄는 dpYn Y, version도 1이라 이렇게 하긴 했는데 다른데서 넣어주는게 나을 것 같음
                travelPictureService.save(fileName, dpSeq, 'Y', travelDiary);
            }
        }
    }

    /**
     * TravelDiary 데이터 세팅
     *
     * @param travelDiaryForm
     * @return
     * @// TODO: 2022/09/18 entity나 repository쪽으로 바꾸는게 좋을 듯
     * @// TODO: 2022/09/18 travelMaster는 이렇게 무조건 객체를 찾아서 넣어주는게 맞는건지..?
     */
    private TravelDiary setTravelDiary(TravelDiaryForm travelDiaryForm) {
        TravelDiary travelDiary = new TravelDiary();

        travelDiary.setTravelMaster(travelMasterRepository.findById(travelDiaryForm.getTravelMasterId()));
        travelDiary.setTravelDate(travelDiaryForm.getTravelDate());
        travelDiary.setTitle(travelDiaryForm.getTitle());
        travelDiary.setContent(travelDiaryForm.getContent());
        travelDiary.setWeather(travelDiaryForm.getWeather());
        travelDiary.setFeelingIcon(travelDiaryForm.getFeelingIcon());
        return travelDiary;
    }

}