package com.workintech.s19challenge.service.user;

import com.ecommerce.backend.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();
    Address findById(long id);
    Address save(Address address);
    Address delete(long id);
}