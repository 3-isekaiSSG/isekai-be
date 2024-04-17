package com.isekai.ssgserver.product.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.product.dto.ProductInfoDto;
import com.isekai.ssgserver.product.entity.ProductKeyword;
import com.isekai.ssgserver.product.repository.ProductKeywordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService {

	private final ProductKeywordRepository productKeywordRepository;

	public List<ProductInfoDto> generalSearchProduct(String keyword) {
		
		AtomicInteger id = new AtomicInteger(0);

		return productKeywordRepository.findDistinctProductCodeByNameContaining(keyword)
			.stream()
			.map(code -> ProductInfoDto.builder()
				.id(id.getAndIncrement())
				.code(code)
				.build())
			.toList();
	}
}
