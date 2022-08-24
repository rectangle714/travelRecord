package com.prj.travelRecord.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class SwaggerTestUpdateUserForm {
	
    @NotBlank
    private String passWord;

    @NotNull
    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력가능합니다")
    private String phoneNumber;

    @NotNull
    @Length( max = 50 )
    private String address;

    public SwaggerTestUpdateUserForm(){
    }

    public SwaggerTestUpdateUserForm(String loginId, String passWord, String phoneNumber, String address) {
        this.passWord = passWord;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


}
