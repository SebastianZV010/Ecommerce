package com.icesi.ecommerce.controller;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.service.impl.ItemService;
import com.icesi.ecommerce.service.interfaces.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final IItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest) {
        ItemResponse createdItem = itemService.createItem(itemRequest);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        ItemResponse item = itemService.getItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        ItemResponse updatedItem = itemService.updateItem(id, itemRequest);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
