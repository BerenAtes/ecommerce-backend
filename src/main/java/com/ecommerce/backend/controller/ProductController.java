package com.ecommerce.backend.controller;


import com.ecommerce.backend.dto.ProductResponse;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.service.CategoryService;
import com.ecommerce.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductResponse> findAll(){
        List<Product> productList=productService.findAll();
        List<ProductResponse> productResponseList=new ArrayList<>();
        productList.forEach(product -> {
            productResponseList.add(new ProductResponse(product.getId(),product.getName(), product.getDescription(),
                    product.getPrice(),product.getStock(),product.getRating(), product.getSellCount()));
        });
        return  productResponseList;
    }


    @GetMapping("/{id}")
    public ProductResponse finByID(@PathVariable Long id){
        Product product=productService.findById(id);
        if (product!=null){
            return new ProductResponse(product.getId(),product.getName(),product.getDescription(),
                    product.getPrice(),product.getStock(),product.getRating(),product.getSellCount());
        }
            throw  new GlobalExceptions("Product is not found with this id: " + id, HttpStatus.NOT_FOUND);
    }

   @PostMapping("/{categoryId}")
    public ProductResponse save(@PathVariable Long categoryId,@RequestBody Product product){
        Category category=categoryService.findByID(categoryId);
        if (categoryId!=null){
            category.getProductList().add(product);
            product.setCategory(category);
            productService.save(product);
        }else {
            throw new GlobalExceptions("Category is not found",HttpStatus.NOT_FOUND);
        }
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),
                product.getPrice(),product.getStock(),product.getRating(),product.getSellCount());
    }


    @PutMapping("/{categoryId}")
    public ProductResponse update(@PathVariable Long categoryId,@RequestBody Product product){
        Category category=categoryService.findByID(categoryId);
        Product newProduct=null;
        for (Product product1:category.getProductList()){
            if (product1.getId()==product.getId()){
                newProduct=product1;
            }

        }
        if (newProduct==null){
            throw new GlobalExceptions("Product is not found",HttpStatus.NOT_FOUND);
        }
        int foundIndexOf= category.getProductList().indexOf(newProduct);
        category.getProductList().set(foundIndexOf,product);
        product.setCategory(category);
        productService.save(product);
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),
                product.getPrice(),product.getStock(),product.getRating(),product.getSellCount());

    }

    @DeleteMapping("/{id}")
    public ProductResponse delete(@PathVariable Long id){
        Product product=productService.findById(id);
        if (product!=null){
            productService.delete(id);
            return new ProductResponse(product.getId(),product.getName(),product.getDescription(),
                    product.getPrice(),product.getStock(),product.getRating(),product.getSellCount());
        }
        else {
            throw new GlobalExceptions("Product is not found",HttpStatus.NOT_FOUND);
        }

    }

}
