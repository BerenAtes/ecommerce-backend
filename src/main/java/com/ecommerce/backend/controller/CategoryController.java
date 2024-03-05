package com.ecommerce.backend.controller;


import com.ecommerce.backend.dto.CategoryAndProductResponse;
import com.ecommerce.backend.dto.CategoryResponse;
import com.ecommerce.backend.dto.ProductResponse;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> findAll(){
        List<Category> categoryList=categoryService.findAll();
        List<CategoryResponse> newList=new ArrayList<>();
        categoryList.forEach(category -> {
            newList.add(new CategoryResponse(category.getId(),category.getTitle(),category.getGender()));
        });
        return newList;
    }

    @GetMapping("/{id}")
    public CategoryAndProductResponse findById(@PathVariable Long id ){
        Category category=categoryService.findByID(id);
        List<ProductResponse> productResponses=new ArrayList<>();
        category.getProductList().forEach(product -> {
            productResponses.add(new ProductResponse(product.getId(),product.getName(),product.getDescription(),
                    product.getPrice(),product.getStock(),product.getRating(),product.getSellCount()));
        });
        return new CategoryAndProductResponse(category.getId(),category.getTitle(),category.getGender(),productResponses);
    }


    @PostMapping
    public Category save(@RequestBody Category category){
       return categoryService.save(category);
    }


    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id,@RequestBody Category category){
        Category newCategory=categoryService.findByID(id);
        if (newCategory!=null){
            categoryService.save(category);
        } else {
            throw new GlobalExceptions("Category is not found with this id: " + id, HttpStatus.NOT_FOUND);
        }
        return new CategoryResponse(category.getId(),category.getTitle(),category.getGender());
    }


    @DeleteMapping("/{id}")
    public CategoryResponse delete(@PathVariable Long id){
        Category category=categoryService.findByID(id);
        if (category!=null){
            categoryService.delete(id);
            return new  CategoryResponse(category.getId(),category.getTitle(),category.getGender());
        }
        throw new GlobalExceptions("Category is not found with this id: " + id,HttpStatus.NOT_FOUND);

    }

}
