package com.kazama.springbootmall.dao;

import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;

public interface UserDao {

    public Integer register(UserRegisterRequest userRegisterRequest);

        public User getUserById(Integer userId);
}
