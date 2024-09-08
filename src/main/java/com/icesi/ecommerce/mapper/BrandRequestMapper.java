package com.icesi.ecommerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.icesi.ecommerce.dto.BrandRequest;
import com.icesi.ecommerce.entity.BrandEntity;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandRequestMapper {

    BrandEntity toBrandEntity(BrandRequest brandRequest);

}