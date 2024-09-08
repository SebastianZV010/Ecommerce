package com.icesi.ecommerce.repository;

import com.icesi.ecommerce.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
