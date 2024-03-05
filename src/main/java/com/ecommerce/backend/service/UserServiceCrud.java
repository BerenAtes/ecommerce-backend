package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.User;

import java.util.List;

public interface UserServiceCrud {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    User delete(Long id);
    User update(Long id,User updatedUser);
}
