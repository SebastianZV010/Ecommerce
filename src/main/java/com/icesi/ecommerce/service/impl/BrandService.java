package com.icesi.ecommerce.service.impl;

import com.icesi.ecommerce.dto.BrandRequest;
import com.icesi.ecommerce.dto.BrandResponse;
import com.icesi.ecommerce.entity.BrandEntity;
import com.icesi.ecommerce.mapper.BrandRequestMapper;
import com.icesi.ecommerce.mapper.BrandResponseMapper;
import com.icesi.ecommerce.repository.IBrandRepository;
import com.icesi.ecommerce.service.interfaces.IBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BrandService  implements IBrandService {

    private final IBrandRepository brandRepository;
    private final BrandResponseMapper brandResponseMapper;
    private final BrandRequestMapper brandRequestMapper;

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        BrandEntity brandEntity = brandRequestMapper.toBrandEntity(brandRequest);
        return brandResponseMapper.toBrandResponse(brandRepository.save(brandEntity));
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        return brandResponseMapper.toBrandResponse(brandRepository.findById(id).orElse(null));
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest brandRequest) {
        BrandEntity brandEntity = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        brandEntity.setName(brandRequest.name());
        brandEntity.setDescription(brandRequest.description());
        return brandResponseMapper.toBrandResponse(brandRepository.save(brandEntity));
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new RuntimeException("Brand not found");
        }
        brandRepository.deleteById(id);
    }
}
