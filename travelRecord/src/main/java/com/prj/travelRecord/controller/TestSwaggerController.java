package com.prj.travelRecord.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.travelRecord.domain.SwaggerTestUpdateUserForm;
import com.prj.travelRecord.domain.SwaggerTestUser;
import com.prj.travelRecord.repository.SwaggerTestUserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"Swagger 테스트를 해봅시다"})
@RestController
@RequestMapping("/swagger_test")
@RequiredArgsConstructor
public class TestSwaggerController {
	
	private final SwaggerTestUserRepository userRepository;
	 
	@ApiOperation(value = "회원 생성(회원가입)")
	@PostMapping("/insert")
	public SwaggerTestUser save( SwaggerTestUser user ){
	    return userRepository.save(user);
	}
	
	@ApiOperation(value = "회원정보 수정")
	@PostMapping("/update/{id}")
	public void update( @PathVariable Long id, SwaggerTestUpdateUserForm updateUser){
	    userRepository.Update(id, updateUser);
	}
	
	@ApiOperation(value = "id로 회원 찾기")
	@PostMapping("/search/{id}")
	public SwaggerTestUser findById (@PathVariable Long id){
	    return userRepository.findById(id);
	}
	
	@ApiOperation(value = "전체 회원 리스트")
	@GetMapping("/list")
	public List<SwaggerTestUser> findAll(){
	    return userRepository.findAll();
	}
	
	//swagger 주소
	//http://localhost:8080/swagger-ui/
	 

}
