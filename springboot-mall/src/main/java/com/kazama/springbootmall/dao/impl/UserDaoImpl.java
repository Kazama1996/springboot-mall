package com.kazama.springbootmall.dao.impl;

import com.kazama.springbootmall.dao.UserDao;
import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        String sql ="INSERT INTO user (email,password, created_date, last_modified_date)  VALUES(:email,:password,:created_date,:last_modified_date)";

        Map<String , Object> map = new HashMap<>();
        map.put("email" , userRegisterRequest.getEmail());
        map.put("password" , userRegisterRequest.getPassword());
        Date now = new Date();
        map.put("created_date" , now);
        map.put("last_modified_date" , now);

        KeyHolder keyHolder = new GeneratedKeyHolder();


        Integer userId = keyHolder.getKey().intValue();

        namedParameterJdbcTemplate.update(sql ,new MapSqlParameterSource(map),keyHolder);

        return userId;


    }

    @Override
    public User getUserById(Integer userId) {

        return null ;
    }
}
