package com.icesi.ecommerce.service.impl;

import com.icesi.ecommerce.entity.BrandEntity;
import com.icesi.ecommerce.repository.IBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.ItemEntity;
import com.icesi.ecommerce.mapper.ItemRequestMapper;
import com.icesi.ecommerce.mapper.ItemResponseMapper;
import com.icesi.ecommerce.repository.IItemRepository;
import com.icesi.ecommerce.service.interfaces.IItemService;

import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService implements IItemService{

    private final IItemRepository itemRepository;
    private final IBrandRepository brandRepository;
    private final ItemResponseMapper itemResponseMapper;
    private final ItemRequestMapper itemRequestMapper;

    @Override
    public ItemResponse createItem(ItemRequest itemRequest) {

        BrandEntity brand = brandRepository.findById(itemRequest.brandId()).orElseThrow(() -> new RuntimeException("Brand not found"));

        ItemEntity itemEntity = itemRequestMapper.toItemEntity(itemRequest);
        itemEntity.setBrand(brand);
        return itemResponseMapper.toItemResponse(itemRepository.save(itemEntity));
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
        return itemResponseMapper.toItemResponse(itemRepository.save(itemEntity));
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


}
