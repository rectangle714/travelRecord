package com.prj.travelRecord.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public class SwaggerTestUserRepository {
	

    // 목록
    private static final Map<Long, SwaggerTestUser> users = new HashMap<>();
    private static long sequence = 0L;


    public SwaggerTestUser save (SwaggerTestUser user){
        user.setId( ++sequence );
        users.put(user.getId(), user);
        return user;
    }

    public SwaggerTestUser findById( Long id) {
        return users.get(id);
    }

    public List<SwaggerTestUser> findAll(){
        return new ArrayList<>(users.values());
    }

    public void Update(Long id, SwaggerTestUpdateUserForm updateUser){
        SwaggerTestUser user = users.get(id);
        user.setPassWord(updateUser.getPassWord());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        user.setAddress(updateUser.getAddress());
    }


}
