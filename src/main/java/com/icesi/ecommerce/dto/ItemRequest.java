package com.icesi.ecommerce.dto;

import java.io.Serializable;
import java.util.Calendar;

public record ItemRequest(
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageURL,
    Calendar createdAt,
    Calendar updatedAt,
    Long brandId
) implements Serializable{   
}
