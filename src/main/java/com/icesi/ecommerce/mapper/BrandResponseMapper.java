package com.icesi.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.BrandResponse;
import com.icesi.ecommerce.entity.BrandEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandResponseMapper {

    BrandResponse toBrandResponse(BrandEntity brandEntity);
}
