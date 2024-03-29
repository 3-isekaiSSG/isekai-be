package com.isekai.ssgserver.seller.service;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.entity.Seller;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerProductRepository sellerProductRepository;

    public SellerDto getSellerByProduct(String productCode) {
        return sellerProductRepository.findByProductCode(productCode)
                .map(s -> SellerDto.builder()
                        .name(s.getSeller().getName())
                        .build())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

    }
}
