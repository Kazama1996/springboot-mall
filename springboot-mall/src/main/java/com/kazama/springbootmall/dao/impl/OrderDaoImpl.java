package com.kazama.springbootmall.dao.impl;

import com.kazama.springbootmall.dao.OrderDao;
import com.kazama.springbootmall.dto.OrderQueryParams;
import com.kazama.springbootmall.model.Order;
import com.kazama.springbootmall.model.OrderItem;
import com.kazama.springbootmall.rowmapper.OrderItemRowMapper;
import com.kazama.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id , total_amount ,created_date , last_modified_date )" +
                " VALUES(:userId, :totalAmount , :created_date ,:last_modified_date)";


        Map<String, Object> map = new HashMap<>();


        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        Date now = new Date();

        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int order_id = keyHolder.getKey().intValue();

        return order_id;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        String sql = "INSERT INTO order_item (order_id , product_id ,quantity,amount) " +
                "VALUES(:order_id , :product_id,:quantity ,:amount );";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for(int i=0 ; i<orderItemList.size(); i++){
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("order_id" , orderId);
            parameterSources[i].addValue("product_id" , orderItem.getProduct_id());
            parameterSources[i].addValue("quantity" , orderItem.getQuantity());
            parameterSources[i].addValue("amount" , orderItem.getAmount());

        }
        namedParameterJdbcTemplate.batchUpdate(sql ,parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id , user_id , total_amount , created_date,last_modified_date " +
                "FROM `order` WHERE order_id = :order_id";

        Map<String,Object> map = new HashMap<>();

        map.put("order_id" , orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql ,map ,new OrderRowMapper());


        if(orderList.size()>0) return orderList.get(0);
        else return null;
    }

    public List<OrderItem> getOrderItemsByOrderId(Integer orderId){
        String sql = "SELECT oi.order_item_id ,oi.order_id ,oi.product_id,oi.quantity,oi.amount,p.product_name,p.image_url" +
                "                FROM order_item as oi" +
                "                LEFT JOIN product as p  ON oi.product_id = p.product_id" +
                "                WHERE oi.order_id = :orderId;";

        Map<String ,Object> map = new HashMap<>();

        map.put("orderId", orderId);
        List<OrderItem> orderItemList=namedParameterJdbcTemplate.query(sql ,map , new OrderItemRowMapper());


        return orderItemList;

    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id ,total_amount ,created_date , last_modified_date FROM `order` WHERE 1=1";

        Map<String ,Object> map = new HashMap<>();

        sql = addFilterinSql(sql, map , orderQueryParams);

        sql = sql + " ORDER BY created_date DESC";

        sql = sql + " LIMIT :limit OFFSET :offset" ;

        map.put("limit" , orderQueryParams.getLimit());
        map.put("offset" , orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql , map , new OrderRowMapper());

        return orderList;
    }

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        String sql  =  "SELECT COUNT(*) FROM `order` WHERE 1=1";
        Map<String ,Object> map = new HashMap<>();

        sql = addFilterinSql(sql , map , orderQueryParams);


        Integer total = namedParameterJdbcTemplate.queryForObject(sql , map , Integer.class);

        return total ;
    }

    private String addFilterinSql(String sql , Map<String , Object> map , OrderQueryParams orderQueryParams){
        if(orderQueryParams.getUserId()!=null){
            sql = sql + " AND user_id = :userId";
            map.put("userId" , orderQueryParams.getUserId());
        }
        return sql ;
    }
}
