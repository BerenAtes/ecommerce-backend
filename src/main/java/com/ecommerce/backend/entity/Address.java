package com.ecommerce.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address",schema = "e-commerce")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "address")
    private List<Order> orderList;


    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "address_user",schema = "e-commerce",joinColumns = @JoinColumn(name = "address_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;

}
