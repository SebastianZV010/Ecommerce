package com.icesi.ecommerce.repository;

import com.icesi.ecommerce.entity.ItemCategoryEntity;
import com.icesi.ecommerce.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IItemCategoryRepository extends JpaRepository<ItemCategoryEntity, Long> {
    void deleteAllByItem(ItemEntity item);
    List<ItemCategoryEntity> findByCategoryId(Long categoryId);
}