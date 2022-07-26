package com.kazama.springbootmall.controller;

import com.kazama.springbootmall.constant.ProductCategory;
import com.kazama.springbootmall.dto.ProductQueryParams;
import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.service.ProductService;
import com.kazama.springbootmall.utill.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件 filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序條件 sorting
            @RequestParam(defaultValue = "created_date") String order_by,
            @RequestParam(defaultValue = "DESC") String sort,
            //分頁
            @RequestParam(defaultValue = "5")@Max(1000)@Min(0) Integer limit,
            @RequestParam(defaultValue = "0")@Min(0) Integer offset) {

        ProductQueryParams productQueryParams = new ProductQueryParams();

        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrder_by(order_by);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> productList = productService.getProducts(productQueryParams);
        Page<Product> page = new Page<>();

        Integer productCount = productService.countProducts(productQueryParams);

        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(productCount);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
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
