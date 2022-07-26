package com.kazama.springbootmall.service;

import com.kazama.springbootmall.dto.UserLoginRequest;
import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public Integer register(UserRegisterRequest userRegisterRequest);

    public User getUserById(Integer userId);


    public User login(UserLoginRequest userLoginRequest);

}
