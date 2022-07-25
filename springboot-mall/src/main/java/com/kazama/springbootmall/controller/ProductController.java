package com.kazama.springbootmall.controller;

import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.service.ProductService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) return ResponseEntity.status(HttpStatus.OK).body(product);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId=productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        if(product!=null)return ResponseEntity.status(HttpStatus.CREATED).body(product);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PutMapping("/products/{product_id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer product_id,
                                                 @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(product_id);

        if(product==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productService.updateProduct(product_id,productRequest);

        Product updateProduct = productService.getProductById(product_id);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);


    }

}
