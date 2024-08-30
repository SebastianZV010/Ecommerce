package com.icesi.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icesi.ecommerce.entity.ItemEntity;

public interface IItemRepository extends JpaRepository<ItemEntity, Long>{
}
