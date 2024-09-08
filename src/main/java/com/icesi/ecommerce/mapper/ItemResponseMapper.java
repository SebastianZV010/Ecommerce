package com.icesi.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemResponseMapper {
    
    ItemResponse toItemResponse(ItemEntity itemEntity);
}
