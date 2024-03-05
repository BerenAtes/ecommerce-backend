package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    Product delete(Long id);
}
