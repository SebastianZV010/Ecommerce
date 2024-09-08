package com.icesi.ecommerce.service.impl;

import com.icesi.ecommerce.entity.BrandEntity;
import com.icesi.ecommerce.entity.CategoryEntity;
import com.icesi.ecommerce.entity.ItemCategoryEntity;
import com.icesi.ecommerce.entity.ItemEntity;
import com.icesi.ecommerce.repository.IBrandRepository;
import com.icesi.ecommerce.repository.ICategoryRepository;
import com.icesi.ecommerce.repository.IItemCategoryRepository;
import com.icesi.ecommerce.repository.IItemRepository;
import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.mapper.ItemRequestMapper;
import com.icesi.ecommerce.mapper.ItemResponseMapper;
import com.icesi.ecommerce.service.interfaces.IItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService implements IItemService{

    private final IItemRepository itemRepository;
    private final IBrandRepository brandRepository;
    private final ICategoryRepository categoryRepository;
    private final IItemCategoryRepository itemCategoryRepository;
    private final ItemResponseMapper itemResponseMapper;
    private final ItemRequestMapper itemRequestMapper;

    @Override
    public ItemResponse createItem(ItemRequest itemRequest) {


        BrandEntity brand = brandRepository.findById(itemRequest.brandId()).orElseThrow(() -> new RuntimeException("Brand not found"));


        ItemEntity itemEntity = itemRequestMapper.toItemEntity(itemRequest);
        itemEntity.setBrand(brand);
        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        // Guardar las categorías del item
        List<ItemCategoryEntity> itemCategories = itemRequest.categoryIds().stream()
                .map(categoryId -> {
                    CategoryEntity category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    return new ItemCategoryEntity(null, savedItemEntity, category);
                }).collect(Collectors.toList());

        itemCategoryRepository.saveAll(itemCategories);

        return itemResponseMapper.toItemResponse(savedItemEntity);
    }

    @Override
    public ItemResponse getItemById(Long id) {
        return itemResponseMapper.toItemResponse(itemRepository.findById(id).orElse(null));
    }

    @Override
    public ItemResponse updateItem(Long id, ItemRequest itemRequest) {

        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));

        BrandEntity brand = brandRepository.findById(itemRequest.brandId()).orElseThrow(() -> new RuntimeException("Brand not found"));

        itemEntity.setName(itemRequest.name());
        itemEntity.setDescription(itemRequest.description());
        itemEntity.setPrice(itemRequest.price());
        itemEntity.setStockQuantity(itemRequest.stockQuantity());
        itemEntity.setBrand(brand);
        itemEntity.setImageURL(itemRequest.imageURL());
        itemEntity.setUpdatedAt(Calendar.getInstance());

        ItemEntity savedItemEntity = itemRepository.save(itemEntity);

        // Primero eliminamos las relaciones actuales con las categorías
        itemCategoryRepository.deleteAllByItem(savedItemEntity);

        // Ahora guardamos las nuevas categorías
        List<ItemCategoryEntity> itemCategories = itemRequest.categoryIds().stream()
                .map(categoryId -> {
                    CategoryEntity category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    return new ItemCategoryEntity(null, savedItemEntity, category);  // Usamos savedItemEntity
                }).collect(Collectors.toList());

        itemCategoryRepository.saveAll(itemCategories);

        return itemResponseMapper.toItemResponse(savedItemEntity);  // Usamos savedItemEntity para devolver la respuesta
    }


    @Override
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found");
        }
        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemResponse> getItemsByBrand(Long brandId) {
        List<ItemEntity> items = itemRepository.findByBrandId(brandId);
        return items.stream()
                .map(itemResponseMapper::toItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemResponse> getItemsByCategory(Long categoryId) {
        // Buscar todas las relaciones item-category por categoryId
        List<ItemCategoryEntity> itemCategories = itemCategoryRepository.findByCategoryId(categoryId);

        // Obtener los items relacionados con esa categoría
        List<ItemEntity> items = itemCategories.stream()
                .map(ItemCategoryEntity::getItem)
                .toList();  // Optimización: usar toList() en lugar de Collectors.toList()

        // Convertir la lista de ItemEntity a ItemResponse
        return items.stream()
                .map(itemResponseMapper::toItemResponse)
                .toList();  // Optimización: usar toList()
    }



}
