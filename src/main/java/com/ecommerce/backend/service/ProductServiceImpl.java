package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
private ProductRepository productRepository;


@Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> optionalProduct=productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        } throw new GlobalExceptions("Product is not found with this id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
    Product product=findById(id);
    if (product!=null){
        productRepository.delete(product);
        return product;
    } throw new GlobalExceptions("Product is not found with this id: " + id,HttpStatus.NOT_FOUND);
    }
}
