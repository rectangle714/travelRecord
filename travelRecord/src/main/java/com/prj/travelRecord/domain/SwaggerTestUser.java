package com.prj.travelRecord.domain;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SwaggerTestUser {
	
    @ApiModelProperty(value = "시퀀스")
    private Long id;

    @ApiModelProperty(value = "사용자 ID")
    @NotBlank
    private String loginId;

    @ApiModelProperty(value = "비밀번호")
    @NotBlank
    private String passWord;

    @ApiModelProperty(value = "핸드폰 번호")
    @NotNull
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String phoneNumber;

    @ApiModelProperty(value = "주소")
    @NotNull
    @Length( max = 50 )
    private String address;

    public SwaggerTestUser(){
    }

    public SwaggerTestUser(String loginId, String passWord, String phoneNumber, String address) {
        this.loginId = loginId;
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
