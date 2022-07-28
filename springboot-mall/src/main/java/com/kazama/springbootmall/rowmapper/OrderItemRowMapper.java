package com.kazama.springbootmall.rowmapper;

import com.kazama.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    public OrderItem mapRow(ResultSet rs , int rowNum) throws SQLException{
        OrderItem orderItem = new OrderItem();

        orderItem.setOrder_id(rs.getInt("order_id"));
        orderItem.setOrder_item_id(rs.getInt("order_item_id"));
        orderItem.setAmount(rs.getInt("amount"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setProduct_id(rs.getInt("product_id"));
        orderItem.setImageUrl(rs.getString("image_url"));
        orderItem.setProduct_name(rs.getString("product_name"));

        return orderItem;
    }
}
