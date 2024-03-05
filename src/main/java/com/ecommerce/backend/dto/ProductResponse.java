package com.ecommerce.backend.dto;

public record ProductResponse(Long id, String name, String description, Double price, Integer stock, Double rating,Long sellCount) {
}
