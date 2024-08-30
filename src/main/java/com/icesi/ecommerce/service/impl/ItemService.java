package com.icesi.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.ItemEntity;
import com.icesi.ecommerce.mapper.ItemRequestMapper;
import com.icesi.ecommerce.mapper.ItemResponseMapper;
import com.icesi.ecommerce.repository.IItemRepository;
import com.icesi.ecommerce.service.interfaces.IItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService implements IItemService{

    private final IItemRepository itemRepository;
    private final ItemResponseMapper itemResponseMapper;
    private final ItemRequestMapper itemRequestMapper;
    
    @Override
    public ItemResponse saveItem(ItemRequest itemRequest){
        ItemEntity itemEntity = itemRequestMapper.toItemEntity(itemRequest);

        return itemResponseMapper.toItemResponse(itemRepository.save(itemEntity));
    }
}
