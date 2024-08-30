package com.icesi.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.entity.ItemEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemRequestMapper {

    ItemEntity toItemEntity(ItemRequest itemRequest);
    
}
