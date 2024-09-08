package com.icesi.ecommerce.dto;

import java.io.Serializable;
import java.util.Calendar;

public record ItemResponse(
    Long id,
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageURL,
    Calendar createdAt,
    Calendar updatedAt,
    String brandName

) implements Serializable{
    
}
