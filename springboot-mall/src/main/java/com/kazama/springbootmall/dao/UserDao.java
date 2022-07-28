package com.kazama.springbootmall.dao;

import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;

public interface UserDao {

    public Integer createUser(UserRegisterRequest userRegisterRequest);

    public User getUserById(Integer userId);

    public User getUserByEmail(String email);
}
