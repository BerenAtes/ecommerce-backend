package com.ecommerce.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category",schema = "e-commerce")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;


    @Column(name = "precision")
    private Double precision;


    @Column(name = "gender")
    private String gender;


    @OneToMany(cascade = CascadeType.ALL,mappedBy= "category")
    private List<Product> productList;
    public void addProduct(Product product){
        if (productList==null){
            productList=new ArrayList<>();
        }
        productList.add(product);
    }
}
