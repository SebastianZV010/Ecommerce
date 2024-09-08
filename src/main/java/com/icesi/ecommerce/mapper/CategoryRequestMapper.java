package com.icesi.ecommerce.mapper;

import com.icesi.ecommerce.dto.CategoryRequest;
import com.icesi.ecommerce.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {

    CategoryEntity toCategoryEntity(CategoryRequest categoryRequest);
}
