package com.kazama.springbootmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazama.springbootmall.dao.UserDao;
import com.kazama.springbootmall.dto.UserLoginRequest;
import com.kazama.springbootmall.dto.UserRegisterRequest;
import com.kazama.springbootmall.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    public void register_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test2@gmail.com");
        userRegisterRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.user_id", notNullValue()))
                .andExpect(jsonPath("$.e_mail", equalTo("test2@gmail.com")))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));

        // 檢查資料庫中的密碼不為明碼
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());
    }
    @Test
    public void register_InvalidEmail() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("12123123");
        userRegisterRequest.setEmail("123");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andExpect(status().is(400));
    }

    @Test
    public void InvalidPassword() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("12123123");
        userRegisterRequest.setEmail("");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andExpect(status().is(400));

    }

    @Test
    public void register_emailAlreadyExist() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("asdasdadasda@gmail.com");
        userRegisterRequest.setPassword("123asdasd456");

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder=MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andExpect(status().is(201));


        mockMvc.perform(requestBuilder).andExpect(status().is(400));

    }


    @Test
    public void login_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("adam@mail.com");
        userRegisterRequest.setPassword("Asd850328");

        register_Account(userRegisterRequest);


        UserLoginRequest userLoginRequest = new UserLoginRequest();

        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword(userLoginRequest.getPassword());

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.user_id", notNullValue()))
                .andExpect(jsonPath("$.e_mail", equalTo(userRegisterRequest.getEmail())))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));

    }
    @Test
    public void login_invalidEmailFormat() throws Exception {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("hkbudsr324");
        userLoginRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_EmailNotFound() throws Exception {


        UserLoginRequest userLoginRequest = new UserLoginRequest();

        userLoginRequest.setEmail("asdasda@gmail.com");
        userLoginRequest.setPassword("123123");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_InvalidPassword() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test123@mail.com");
        userRegisterRequest.setPassword("Asd850328");

        register_Account(userRegisterRequest);


        UserLoginRequest userLoginRequest = new UserLoginRequest();

        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword("1231231231231");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }
    
    
    private void register_Account(UserRegisterRequest userRegisterRequest) throws Exception {
        
            String json= objectMapper.writeValueAsString(userRegisterRequest);
            RequestBuilder requestBuilder  = MockMvcRequestBuilders.post("/user/register")
                            .contentType(MediaType.APPLICATION_JSON)
                                    .content(json);
            
            mockMvc.perform(requestBuilder).andExpect(status().is(201));
            
    }

}