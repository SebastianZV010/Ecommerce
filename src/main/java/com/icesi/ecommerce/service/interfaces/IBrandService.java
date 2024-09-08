package com.icesi.ecommerce.service.interfaces;

import com.icesi.ecommerce.dto.BrandRequest;
import com.icesi.ecommerce.dto.BrandResponse;

public interface IBrandService {
    BrandResponse createBrand(BrandRequest brandRequest);
    BrandResponse getBrandById(Long id);
    BrandResponse updateBrand(Long id, BrandRequest brandRequest);
    void deleteBrand(Long id);
}
