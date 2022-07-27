package com.kazama.springbootmall.dto;

import javax.validation.constraints.NotNull;

public class BuyItem {

    @NotNull
    private Integer product_id;
    @NotNull
    private Integer quantity;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
