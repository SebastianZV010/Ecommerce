package com.icesi.ecommerce.entity;


import java.util.Calendar;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="item")
public class ItemEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description")
    @Size(max = 100)
    private String description;
    @Column(name = "price", nullable = false)
    private Double price;
    @Min(value = 0)
    @Column(name = "stockQuantity", nullable = false)
    private Integer stockQuantity;
    @Column(name = "imageURL")
    private String imageURL;
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Calendar createdAt;
    @Column(name = "updatedAt")
    private Calendar updatedAt;

}
