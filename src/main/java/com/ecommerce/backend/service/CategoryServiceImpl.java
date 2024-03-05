package com.ecommerce.backend.service;


import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByID(Long id) {
        Optional<Category> optionalCategory= categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        } throw new GlobalExceptions("No category found with this id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category delete(Long id) {
        Category category=findByID(id);
        if (category!=null){
            categoryRepository.delete(category);
            return category;
        }
        throw new GlobalExceptions("No category found with this id: " + id,HttpStatus.NOT_FOUND);
    }
}
