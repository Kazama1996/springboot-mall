package com.kazama.springbootmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.kazama.springbootmall.constant.ProductCategory;
import com.kazama.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getPoduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/{productId}", 1);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.product_name", equalTo("蘋果（澳洲）")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.image_url", notNullValue()))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.stock", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));
    }

    @Test
    public void getProduct_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/{productId}", 50000);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));

    }

    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("test food");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImage_url("http://test.com");
        productRequest.setPrice(500);
        productRequest.setStock(1000);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.product_name", equalTo("test food")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.image_url", equalTo("http://test.com")))
                .andExpect(jsonPath("$.price", equalTo(500)))
                .andExpect(jsonPath("$.stock", equalTo(1000)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.created_date", notNullValue()))
                .andExpect(jsonPath("$.last_modified_date", notNullValue()));

    }

    @Transactional
    @Test
    public void creatreProduct_illeagelArguments() throws Exception {

        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("Apple  test");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void updateProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProduct_name("Orange");
        productRequest.setCategory(ProductCategory.FOOD);
        productRequest.setImage_url("http://orangeImg.com");
        productRequest.setPrice(500);
        productRequest.setStock(2000);
        productRequest.setDescription("I m orange");


        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.product_name", equalTo("Orange")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.image_url", equalTo("http://orangeImg.com")))
                .andExpect(jsonPath("$.price", equalTo(500)))
                .andExpect(jsonPath("$.stock", equalTo(2000)))
                .andExpect(jsonPath("$.description", equalTo("I m orange")));

    }

    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception {

        // create a request.
        ProductRequest productRequest = setARequest(true);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productId}", 5000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));

    }
    @Test
    @Transactional
    public void updateProduct_illeagalArgument() throws Exception {
        //create a incorrect productRequest.
        ProductRequest productRequest = setARequest(false);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productId}" , 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    public void deleteProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{productId}" ,1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Test
    @Transactional
    public void deleteProduct_productNonExistingProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{productId}" ,50000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Test
    public void get_ProductList() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.limit" , notNullValue()))
                .andExpect(jsonPath("$.offset",notNullValue()))
                .andExpect(jsonPath("$.total",notNullValue()))
                .andExpect(jsonPath("$.results",hasSize(5)));
    }

    @Test
    public void getProductList_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("search","B")
                .param("category","CAR");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("limit" ,notNullValue()))
                .andExpect(jsonPath("$.offset",notNullValue()))
                .andExpect(jsonPath("$.total",notNullValue()))
                .andExpect(jsonPath("$.results",hasSize(2)));

    }

    @Test
    public void getProductList_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("order_by" , "price")
                .param("sort","DESC");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].product_id", equalTo(6)))
                .andExpect(jsonPath("$.results[1].product_id", equalTo(5)))
                .andExpect(jsonPath("$.results[2].product_id", equalTo(7)))
                .andExpect(jsonPath("$.results[3].product_id", equalTo(4)))
                .andExpect(jsonPath("$.results[4].product_id", equalTo(2)));
    }
    @Test
    public void getProductList_paging() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("limit", "2")
                .param("offset" , "2");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.results",hasSize(2)))
                .andExpect(jsonPath("$.results[0].product_id", equalTo(5)))
                .andExpect(jsonPath("$.results[1].product_id", equalTo(4)));


    }






    private ProductRequest setARequest(boolean isCorrectRequest) {
        ProductRequest pRequest = new ProductRequest();

        pRequest.setProduct_name("Orange");
        pRequest.setCategory(ProductCategory.FOOD);
        pRequest.setImage_url("http://Orange.com");
        pRequest.setPrice(100);
        pRequest.setStock(10);

        // mess up the data.
        if (isCorrectRequest == false) pRequest.setPrice(null);

        return pRequest;

    }



}