package com.icesi.ecommerce.service.interfaces;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;

import java.util.List;

public interface IItemService {
    ItemResponse createItem(ItemRequest itemRequest);
    ItemResponse getItemById(Long id);
    ItemResponse updateItem(Long id, ItemRequest itemRequest);
    void deleteItem(Long id);
}
