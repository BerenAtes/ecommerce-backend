package com.ecommerce.backend.service;


import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } throw new GlobalExceptions("Order is not found with this id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order delete(Long id) {
        Order order=findById(id);
        if (order!=null){
            orderRepository.delete(order);
            return order;
        }   throw new GlobalExceptions("Order is not found with this id: " + id,HttpStatus.NOT_FOUND);
    }
}
