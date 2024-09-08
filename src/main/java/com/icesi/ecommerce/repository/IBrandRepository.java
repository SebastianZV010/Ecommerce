package com.icesi.ecommerce.repository;

import com.icesi.ecommerce.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
}