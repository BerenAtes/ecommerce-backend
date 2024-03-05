package com.ecommerce.backend.dto;

public record RegisterUser(String fullName,String email,String password,Long roleId) {
}
