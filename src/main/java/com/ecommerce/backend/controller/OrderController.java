package com.ecommerce.backend.controller;


import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.service.OrderService;
import com.ecommerce.backend.util.Converter;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.workintech.s19challenge.service.user.AddressService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private AddressService addressService;



    @Autowired
    public OrderController(OrderService orderService, AddressService addressService) {
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @GetMapping
        public List<OrderResponse> findAll(){
        List<Order> orderList=orderService.findAll();
        return Converter.orderResponseCon(orderList);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id){
        return Converter.orderResponseCon(orderService.findById(id));
    }


    @PostMapping("/{addressId}")
    public OrderResponse save(@PathVariable Long addressId,@RequestBody Order order){
        Address address=addressService.findById(addressId);
        if (address!=null){
            address.getOrderList().add(order);
            order.setAddress(address);
            List<Product> productList=order.getProductList();
            for (Product product:productList){
                order.addProduct(product);
            }
            orderService.save(order);
        } else {
            throw new GlobalExceptions("Order is not found with this id: " + addressId, HttpStatus.NOT_FOUND);
        }
        return Converter.orderResponseCon(order);
    }


    @PutMapping("/{addressId}")
    public OrderResponse update(@RequestBody Order order, @PathVariable long addressId){
        Address address = addressService.findById(addressId);
        Order foundOrder = null;
        for(Order order1:address.getOrderList()){
            if(order1.getId() == order.getId()){
                foundOrder = order1;
            }
        }
        if(foundOrder == null){
            throw new GlobalExceptions ("Order is not found", HttpStatus.BAD_REQUEST);
        }
        int indexOfFound = address.getOrderList().indexOf(foundOrder);
        address.getOrderList().set(indexOfFound,order);
        order.setAddress(address);

        order.setProductList(foundOrder.getProductList());
        orderService.save(order);

        return Converter.orderResponseCon(order);
    }


    @DeleteMapping("/{id}")
    public Order delete(@PathVariable Long id){
        Order order=orderService.findById(id);
        if (order!=null){
            orderService.delete(id);
            return order;
        }
        throw new GlobalExceptions("Order is not found with this id: " + id,HttpStatus.NOT_FOUND);

    }

}
