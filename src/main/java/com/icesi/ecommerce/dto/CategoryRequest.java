package com.icesi.ecommerce.dto;

import java.io.Serializable;

public record CategoryRequest(
        String name,
        String description
) implements Serializable {
}
