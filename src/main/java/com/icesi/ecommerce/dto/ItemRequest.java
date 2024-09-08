package com.icesi.ecommerce.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public record ItemRequest(
    String name,
    String description,
    Double price,
    Integer stockQuantity,
    String imageURL,
    Calendar createdAt,
    Calendar updatedAt,
    Long brandId,
    List<Long> categoryIds
) implements Serializable{   
}
