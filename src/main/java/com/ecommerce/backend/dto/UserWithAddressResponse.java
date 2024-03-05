package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.Address;

import java.util.List;

public record UserWithAddressResponse(Long id, String name, String email, List<Address> addressList) {
}
