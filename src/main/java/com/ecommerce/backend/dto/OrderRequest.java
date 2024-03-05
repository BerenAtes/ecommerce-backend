package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<Product> productList;
    private Order order;
    private User user;
}
