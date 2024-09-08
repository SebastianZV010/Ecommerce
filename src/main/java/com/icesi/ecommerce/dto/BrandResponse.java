package com.icesi.ecommerce.dto;

import java.io.Serializable;


public record BrandResponse(
        Long id,
        String name,
        String description
) implements Serializable {
}