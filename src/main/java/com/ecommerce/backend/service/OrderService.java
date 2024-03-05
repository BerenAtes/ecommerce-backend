package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    Order delete(Long id);
}
