package com.icesi.ecommerce.service.interfaces;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;

public interface IItemService {
    
    ItemResponse saveItem(ItemRequest itemRequest);
}
