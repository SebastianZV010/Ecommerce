package com.icesi.ecommerce.service.impl;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.BrandEntity;
import com.icesi.ecommerce.entity.CategoryEntity;
import com.icesi.ecommerce.entity.ItemEntity;
import com.icesi.ecommerce.entity.ItemCategoryEntity;
import com.icesi.ecommerce.mapper.ItemRequestMapper;
import com.icesi.ecommerce.mapper.ItemResponseMapper;
import com.icesi.ecommerce.repository.IBrandRepository;
import com.icesi.ecommerce.repository.ICategoryRepository;
import com.icesi.ecommerce.repository.IItemCategoryRepository;
import com.icesi.ecommerce.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private IItemRepository itemRepository;

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private ICategoryRepository categoryRepository; // Mock para ICategoryRepository

    @Mock
    private IItemCategoryRepository itemCategoryRepository;

    @Mock
    private ItemResponseMapper itemResponseMapper;

    @Mock
    private ItemRequestMapper itemRequestMapper;

    @InjectMocks
    private ItemService itemService;

    private ItemRequest itemRequest;
    private ItemResponse itemResponse;
    private ItemEntity itemEntity;
    private BrandEntity brandEntity;
    private CategoryEntity categoryEntity;
    private ItemCategoryEntity itemCategoryEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brandEntity = new BrandEntity(1L, "Brand1", "Brand Description");
        categoryEntity = new CategoryEntity(1L, "Category1", "Category Description");

        itemRequest = new ItemRequest("name1", "Description1", 10.00, 1, "url1", Calendar.getInstance(), Calendar.getInstance(), 1L, Arrays.asList(1L));
        itemEntity = new ItemEntity(1L, "name1", "Description1", 100.0, 10, "url1", Calendar.getInstance(), Calendar.getInstance(), brandEntity, Arrays.asList(itemCategoryEntity));

        itemResponse = new ItemResponse(1L, "name1", "Description1", 100.0, 10, "url1", Calendar.getInstance(), Calendar.getInstance(), "Brand1", Arrays.asList("Category1"));

        itemCategoryEntity = new ItemCategoryEntity(1L, itemEntity, categoryEntity);
    }

    @Test
    void createItem_success() {
        // Arrange
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brandEntity));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryEntity)); // Mockear categoría
        when(itemRequestMapper.toItemEntity(any(ItemRequest.class))).thenReturn(itemEntity);
        when(itemRepository.save(any(ItemEntity.class))).thenReturn(itemEntity);
        when(itemResponseMapper.toItemResponse(any(ItemEntity.class))).thenReturn(itemResponse);

        // Act
        ItemResponse result = itemService.createItem(itemRequest);

        // Assert
        assertNotNull(result);
        assertEquals(itemResponse.id(), result.id());
        verify(itemRepository, times(1)).save(any(ItemEntity.class));
        verify(categoryRepository, times(1)).findById(anyLong()); // Verificar que se llamó a categoryRepository
    }

    @Test
    void updateItem_success() {
        // Arrange
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brandEntity));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryEntity)); // Mockear categoría
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
        verify(categoryRepository, times(1)).findById(anyLong()); // Verificar que se llamó a categoryRepository
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

    // Test para el método getItemsByBrand
    @Test
    void getItemsByBrand_success() {
        // Arrange
        when(itemRepository.findByBrandId(anyLong())).thenReturn(Arrays.asList(itemEntity));
        when(itemResponseMapper.toItemResponse(any(ItemEntity.class))).thenReturn(itemResponse);

        // Act
        List<ItemResponse> result = itemService.getItemsByBrand(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(itemResponse.id(), result.get(0).id());
        verify(itemRepository, times(1)).findByBrandId(1L);
    }

    @Test
    void getItemsByBrand_noItemsFound() {
        // Arrange
        when(itemRepository.findByBrandId(anyLong())).thenReturn(Arrays.asList());

        // Act
        List<ItemResponse> result = itemService.getItemsByBrand(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRepository, times(1)).findByBrandId(1L);
    }

    // Nuevo test para el método getItemsByCategory
    @Test
    void getItemsByCategory_success() {
        // Arrange
        when(itemCategoryRepository.findByCategoryId(anyLong())).thenReturn(Arrays.asList(itemCategoryEntity));
        when(itemResponseMapper.toItemResponse(any(ItemEntity.class))).thenReturn(itemResponse);

        // Act
        List<ItemResponse> result = itemService.getItemsByCategory(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(itemResponse.id(), result.get(0).id());
        verify(itemCategoryRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    void getItemsByCategory_noItemsFound() {
        // Arrange
        when(itemCategoryRepository.findByCategoryId(anyLong())).thenReturn(Arrays.asList());

        // Act
        List<ItemResponse> result = itemService.getItemsByCategory(1L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemCategoryRepository, times(1)).findByCategoryId(1L);
    }
}
