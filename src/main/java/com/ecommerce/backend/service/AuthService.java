package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Role;

import java.util.List;

public interface AuthService {
    List<Role> findAll();
    Address findById(long id);
    Address save(Address address);
    Address delete(long id);
}
