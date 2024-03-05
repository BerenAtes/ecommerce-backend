package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "order",schema = "e-commerce")

    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;


        @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST})
        @JoinColumn(name = "address_id")
        private Address address;


       @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
       @JoinTable(name = "product_order",schema = "e-commerce",joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns=@JoinColumn(name = "product_id"))
        private List<Product> productList;

       public void addProduct(Product product){
           if (productList==null){
               productList=new ArrayList<>();
           }
           productList.add(product);
       }


       @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
       @JoinColumn(name = "user_id")
       private User user;

    }
