package com.ecommerce.backend.controller;
import com.ecommerce.backend.dto.AddressResponse;
import com.ecommerce.backend.dto.AddressWithOrderResponse;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.service.UserServiceCrud;
import com.workintech.s19challenge.service.user.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;
    private UserServiceCrud userCrudService;

    @Autowired
    public AddressController(AddressService addressService, UserServiceCrud userCrudService) {
        this.addressService = addressService;
        this.userCrudService = userCrudService;
    }

    @GetMapping
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public AddressWithOrderResponse findById( @PathVariable long id) {
        Address address = addressService.findById(id);
        List<Order> orderList = new ArrayList<>();
        address.getOrderList().forEach(order -> {
            orderList.add(order);
        });
        return new AddressWithOrderResponse(address.getId(), address.getTitle(), address.getName(),
                address.getSurname(), address.getPhoneNumber(), address.getCity(), address.getDistrict(), address.getAddress(), orderList);
    }

    @PostMapping("/{userId}")
    public AddressResponse save(@Validated @PathVariable long userId, @RequestBody Address address) {
        User user = userCrudService.findById(userId);
        if (user != null) {
            user.getAddresses().add(address);
            address.setUser((List<User>) user);
            addressService.save(address);
        } else {
            throw new GlobalExceptions("User is not found with id: " + userId, HttpStatus.NOT_FOUND);
        }
        return new AddressResponse(address.getId(), address.getTitle(), address.getName(), address.getSurname(),
                address.getPhoneNumber(), address.getCity(), address.getDistrict(),
                address.getAddress());
    }

    @PutMapping("/{userId}")
    public Address update(@RequestBody Address address, @PathVariable long userId) {
        User user = userCrudService.findById(userId);
        Address foundAddress = null;
        for (Address address1 : user.getAddresses()) {
            if (address1.getId() == address.getId()) {
                foundAddress = address1;
            }
        }
        if (foundAddress == null) {
            throw new GlobalExceptions("Address is not found", HttpStatus.BAD_REQUEST);
        }
        int indexOfFound = user.getAddresses().indexOf(foundAddress);
        user.getAddresses().set(indexOfFound, address);
        address.setUser((List<User>) user);
        addressService.save(address);
        return address;
    }

    @DeleteMapping("/{id}")
    public Address delete( @PathVariable long id) {
        Address address = addressService.findById(id);
        if (address != null) {
            addressService.delete(id);
            return address;
        }
        throw new GlobalExceptions("Address is not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
