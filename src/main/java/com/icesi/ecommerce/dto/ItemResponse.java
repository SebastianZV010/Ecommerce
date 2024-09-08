package com.icesi.ecommerce.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public record ItemResponse(
    Long id,
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageURL,
    Calendar createdAt,
    Calendar updatedAt,
    String brandName,
    List<String> categoryNames

) implements Serializable{
    
}
