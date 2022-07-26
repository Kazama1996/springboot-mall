package com.kazama.springbootmall.service.impl;


import com.kazama.springbootmall.dao.UserDao;
import com.kazama.springbootmall.dto.UserLoginRequest;
import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;
import com.kazama.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查有沒有已經被註冊的email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        //log {} 裡面 會是 後面的值 也就是Eamil 有點像 C的 %d %s 之類的
        if(user!=null){
            log.warn("Email {} 已經被註冊" , userRegisterRequest.getEmail());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5 生成Hash Value

        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);
        return userDao.createUser(userRegisterRequest);

    }

    @Override
    public User getUserById(Integer userId) {

        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        System.out.println(user);

        //檢查user 是否存在
        if(user==null) {
            log.warn("Email : {} 尚未註冊" , userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用MD5生成 密碼的Hash Value

        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //比較密碼
        if(user.getPassword().equals(hashedPassword)){
            return user;
        }
        else {
            log.warn("帳號密碼不正確");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
