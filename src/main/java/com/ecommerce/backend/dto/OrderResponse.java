package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;

import java.util.List;

public record OrderResponse(Long id,  List<Product> productList, User user,Address address ) {
}
