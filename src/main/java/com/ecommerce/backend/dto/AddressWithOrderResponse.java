package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.Order;

import java.util.List;

public record AddressWithOrderResponse(Long id, String title, String name, String surname, String phoneNumber, String city
        , String district, String address, List<Order> orderList) {
}
