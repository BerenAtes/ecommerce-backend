package com.ecommerce.backend.controller;


import com.ecommerce.backend.dto.UserResponse;
import com.ecommerce.backend.dto.UserWithAddressResponse;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.OrderService;
import com.ecommerce.backend.service.UserServiceCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
private UserServiceCrud userServiceCrud;

    @Autowired
    public UserController(UserServiceCrud userServiceCrud) {
        this.userServiceCrud = userServiceCrud;
    }
    @GetMapping
    public List<User> findAll(){
        return userServiceCrud.findAll();
    }

    @GetMapping("/{id}")
    public UserWithAddressResponse findById(@PathVariable long id){
        User user = userServiceCrud.findById(id);
        List<Address> addressList = new ArrayList<>();
        user.getAddresses().forEach(address -> {
            addressList.add(address);
        });
        return new UserWithAddressResponse(user.getId(), user.getFullName(), user.getEmail(), user.getAddresses());
    }

    @PutMapping("/{id}")
    public UserResponse updateUserInfo(@PathVariable Long id, @RequestBody User updatedUserInfo) {
        User updatedUserEntity = userServiceCrud.update(id, updatedUserInfo);
        return new UserResponse(updatedUserEntity.getId(), updatedUserEntity.getFullName(), updatedUserEntity.getEmail());
    }


    @DeleteMapping("/{id}")
    public UserResponse delete( @PathVariable Long id){
        User user = userServiceCrud.findById(id);
        userServiceCrud.delete(id);
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }
}
