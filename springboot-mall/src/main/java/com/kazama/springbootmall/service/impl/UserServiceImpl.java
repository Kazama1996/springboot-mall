package com.kazama.springbootmall.service.impl;


import com.kazama.springbootmall.dao.UserDao;
import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;
import com.kazama.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        return userDao.register(userRegisterRequest);


    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
