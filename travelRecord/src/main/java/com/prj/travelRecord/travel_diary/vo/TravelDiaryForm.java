package com.prj.travelRecord.travel_diary.vo;

import com.prj.travelRecord.domain.FeelingIcon;
import com.prj.travelRecord.domain.TravelMaster;
import com.prj.travelRecord.domain.Weather;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TravelDiaryForm {

    private long id;

    private long travelMasterId;

    private String title;

    private String content;

    private FeelingIcon feelingIcon;

    private Weather weather;

    private String travelDate;

    private LocalDateTime registerDate;

    private String registerId;

    private LocalDateTime updateDate;

    private String updateId;

//    private MultipartFile attachFile;

    //front단에서 사진을 dp할 순서대로 보내줘야함
    private ArrayList<MultipartFile> attachFiles;

}
