package com.icesi.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.ItemRequest;
import com.icesi.ecommerce.entity.ItemEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemRequestMapper {

    @Mapping(target = "brand.id", source = "brandId") // Mapeo de brandId a la propiedad brand en ItemEntity
    ItemEntity toItemEntity(ItemRequest itemRequest);

}