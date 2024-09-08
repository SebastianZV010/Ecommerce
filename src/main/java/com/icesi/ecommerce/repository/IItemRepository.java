package com.icesi.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icesi.ecommerce.entity.ItemEntity;

import java.util.List;

public interface IItemRepository extends JpaRepository<ItemEntity, Long>{
    List<ItemEntity> findByBrandId(Long brandId);
}
