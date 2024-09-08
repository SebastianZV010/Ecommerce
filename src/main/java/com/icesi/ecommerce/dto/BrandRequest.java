package com.icesi.ecommerce.dto;

import java.io.Serializable;

public record BrandRequest(
        String name,
        String description
) implements Serializable {
}
