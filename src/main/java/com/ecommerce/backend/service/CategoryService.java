package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findByID(Long id);
    Category save(Category category);
    Category delete(Long id);

}
