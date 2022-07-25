package com.kazama.springbootmall.controller;

import com.kazama.springbootmall.constant.ProductCategory;
import com.kazama.springbootmall.dto.ProductQueryParams;
import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.service.ProductService;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //查詢條件 filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序條件 sorting
            @RequestParam(defaultValue = "created_date") String order_by,
            @RequestParam(defaultValue = "DESC") String sort) {

        ProductQueryParams productQueryParams = new ProductQueryParams();

        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrder_by(order_by);
        productQueryParams.setSort(sort);


        List<Product> productList = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if (product != null) return ResponseEntity.status(HttpStatus.OK).body(product);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        if (product != null) return ResponseEntity.status(HttpStatus.CREATED).body(product);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/products/{product_id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer product_id,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        Product product = productService.getProductById(product_id);

        if (product == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        productService.updateProduct(product_id, productRequest);

        Product updateProduct = productService.getProductById(product_id);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);

    }

    @DeleteMapping("/products/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProductById(product_id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
