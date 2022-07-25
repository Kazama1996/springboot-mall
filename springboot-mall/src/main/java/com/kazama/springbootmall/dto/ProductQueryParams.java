package com.kazama.springbootmall.dto;

import com.kazama.springbootmall.constant.ProductCategory;

public class ProductQueryParams {
    private ProductCategory category;
    private  String search;

    private String order_by ;

    private String sort;

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
