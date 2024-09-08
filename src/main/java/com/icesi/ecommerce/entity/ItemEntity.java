package com.icesi.ecommerce.entity;


import java.util.Calendar;
import java.util.List;

import jakarta.validation.constraints.*;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;
    @Column(name = "imageURL")
    private String imageURL;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Calendar createdAt;
    @Column(name = "updated_at")
    private Calendar updatedAt;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCategoryEntity> itemCategories;
}
