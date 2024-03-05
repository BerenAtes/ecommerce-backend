package com.ecommerce.backend.dto;

import java.util.List;

public record CategoryAndProductResponse(Long id, String title, String gender, List<ProductResponse> productList) {
}
