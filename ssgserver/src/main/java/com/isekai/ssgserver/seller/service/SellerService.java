package com.isekai.ssgserver.seller.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.seller.dto.BrandNameResponseDto;
import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.repository.BrandNameCustomRepository;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerService {

	private final SellerProductRepository sellerProductRepository;
	private final BrandNameCustomRepository brandNameCustomRepository;

	public SellerDto getSellerByProduct(String productCode) {
		return sellerProductRepository.findByProductCode(productCode)
			.map(s -> SellerDto.builder()
				.sellerId(s.getSeller().getSellerId())
				.name(s.getSeller().getName())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

	}

	public List<BrandNameResponseDto> getSellerByProduct(String largeName, String mediumName, String smallName,
		String criteria) {

		List<BrandNameResponseDto> results = brandNameCustomRepository.findBrandProductCountByCategory(
			largeName, mediumName, smallName, criteria);
		AtomicInteger responseId = new AtomicInteger(0);

		List<BrandNameResponseDto> brandNameResponse = results.stream()
			.map(bn -> BrandNameResponseDto.builder()
				.id(responseId.getAndIncrement())
				.name(bn.getName())
				.cnt(bn.getCnt())
				.build())
			.toList();

		return brandNameResponse;
	}
}
