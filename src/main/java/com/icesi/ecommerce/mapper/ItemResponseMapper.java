package com.icesi.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.ItemResponse;
import com.icesi.ecommerce.entity.ItemEntity;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ItemResponseMapper {

    @Mapping(target = "brandName", source = "brand.name") // Mapeo del nombre de la marca a brandName en ItemResponse
    ItemResponse toItemResponse(ItemEntity itemEntity);

}
