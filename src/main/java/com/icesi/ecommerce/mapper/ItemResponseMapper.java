package com.icesi.ecommerce.mapper;

import com.icesi.ecommerce.entity.ItemCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.ItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemResponseMapper {

    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "categoryNames", expression = "java(mapCategoryNames(itemEntity.getItemCategories()))")
    ItemResponse toItemResponse(ItemEntity itemEntity);

    // Método auxiliar para mapear los nombres de las categorías
    default List<String> mapCategoryNames(List<ItemCategoryEntity> itemCategories) {
        return itemCategories.stream()
                .map(itemCategory -> itemCategory.getCategory().getName())
                .collect(Collectors.toList());
    }
}
