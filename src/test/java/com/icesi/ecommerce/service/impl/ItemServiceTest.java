package com.icesi.ecommerce.service.impl;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.ItemEntity;
import com.icesi.ecommerce.mapper.ItemRequestMapper;
import com.icesi.ecommerce.mapper.ItemResponseMapper;
import com.icesi.ecommerce.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private IItemRepository itemRepository;

    @Mock
    private ItemResponseMapper itemResponseMapper;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @InjectMocks
    private ItemService itemService;

    private ItemRequest itemRequest;
    private ItemResponse itemResponse;
    private ItemEntity itemEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up sample data for tests
        itemRequest = new ItemRequest("name1", "Description1", 10.00, 1,"url1",Calendar.getInstance(),Calendar.getInstance());
        itemEntity = new ItemEntity(1L, "name1", "Description1", 100.0, 10, "url1", Calendar.getInstance(), Calendar.getInstance());
        itemResponse = new ItemResponse(1L, "name1", "Description1", 100.0, 10, "url1",Calendar.getInstance(), Calendar.getInstance());
    }

    @Test
    void createItem_success() {
        // Arrange
        when(itemRequestMapper.toItemEntity(any(ItemRequest.class))).thenReturn(itemEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);
        when(itemResponseMapper.toItemResponse(any(ItemEntity.class))).thenReturn(itemResponse);

        // Act
        ItemResponse result = itemService.createItem(itemRequest);

        // Assert
        assertNotNull(result);
        assertEquals(itemResponse.id(), result.id());
        verify(itemRepository, times(1)).save(any(ItemEntity.class));
    }

    @Test
    void getItemById_success() {
        // Arrange
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(itemEntity));
        when(itemResponseMapper.toItemResponse(any(ItemEntity.class))).thenReturn(itemResponse);

        // Act
        ItemResponse result = itemService.getItemById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(itemResponse.id(), result.id());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void getItemById_notFound() {
        // Arrange
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        ItemResponse result = itemService.getItemById(1L);

        // Assert
        assertNull(result);
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void updateItem_success() {
        // Arrange
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(itemEntity));
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);
        when(itemResponseMapper.toItemResponse(any(ItemEntity.class))).thenReturn(itemResponse);

        // Act
        ItemResponse result = itemService.updateItem(1L, itemRequest);

        // Assert
        assertNotNull(result);
        assertEquals(itemResponse.id(), result.id());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(any(ItemEntity.class));
    }

    @Test
    void updateItem_notFound() {
        // Arrange
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemService.updateItem(1L, itemRequest);
        });
        assertEquals("Item not found", exception.getMessage());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, never()).save(any(ItemEntity.class));
    }

    @Test
    void deleteItem_success() {
        // Arrange
        when(itemRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(itemRepository).deleteById(anyLong());

        // Act
        itemService.deleteItem(1L);

        // Assert
        verify(itemRepository, times(1)).existsById(1L);
        verify(itemRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteItem_notFound() {
        // Arrange
        when(itemRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemService.deleteItem(1L);
        });
        assertEquals("Item not found", exception.getMessage());
        verify(itemRepository, times(1)).existsById(1L);
        verify(itemRepository, never()).deleteById(anyLong());
    }
}
