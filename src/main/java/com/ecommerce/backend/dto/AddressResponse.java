package com.ecommerce.backend.dto;

public record AddressResponse(Long id, String title,String name, String surname, String phoneNumber,String city
        ,String district, String address) {
}
