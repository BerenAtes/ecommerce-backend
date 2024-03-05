package com.ecommerce.backend.util;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<OrderResponse> orderResponseCon(List<Order> orderList){
    List<OrderResponse> orderResponseList=new ArrayList<>();
    for (Order order:orderList){
        orderResponseList.add(new OrderResponse(order.getId(),order.getProductList(),order.getUser(),order.getAddress()));
    }
    return orderResponseList;
    }

    public static OrderResponse orderResponseCon(Order order){
        return new OrderResponse(order.getId(),order.getProductList(),order.getUser(),order.getAddress());
    }
}
