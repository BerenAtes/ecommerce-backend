package com.ecommerce.backend.service;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.exceptions.GlobalExceptions;
import com.ecommerce.backend.repository.AddressRepository;
import com.workintech.s19challenge.service.user.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;


    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()){
            return optionalAddress.get();
        }
        throw new GlobalExceptions("Address is not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address delete(long id) {
        Address address = findById(id);
        if(address != null){
            addressRepository.delete(address);
            return address;
        }
        throw new GlobalExceptions("Address is not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
