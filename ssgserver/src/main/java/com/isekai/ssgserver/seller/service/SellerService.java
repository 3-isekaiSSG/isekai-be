package com.isekai.ssgserver.seller.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

	private final SellerProductRepository sellerProductRepository;

	public SellerDto getSellerByProduct(String productCode) {
		return sellerProductRepository.findByProductCode(productCode)
			.map(s -> SellerDto.builder()
				.sellerId(s.getSeller().getSellerId())
				.name(s.getSeller().getName())
				.build())
			.orElse(SellerDto.builder().build());
	}
}
