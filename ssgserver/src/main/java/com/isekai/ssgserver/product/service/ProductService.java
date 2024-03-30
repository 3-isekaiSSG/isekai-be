package com.isekai.ssgserver.product.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryProductCustomRepository;
import com.isekai.ssgserver.product.dto.CategoryProductResponseDto;
import com.isekai.ssgserver.product.dto.ProductInfoDto;
import com.isekai.ssgserver.product.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

	private final CategoryProductCustomRepository categoryProductCustomRepository;

	// 카테고리 상품 조회
	public CategoryProductResponseDto getCategoryProduct(String largeName, String mediumName, String smallName,
		Integer index, String criteria, String brandName, String dType, Integer minPrc, Integer maxPrc) {

		// pageable 객체 생성
		Pageable pageable = PageRequest.of(index, 40);
		Page<Product> productPage = categoryProductCustomRepository.findCategoryProduct(
			largeName, mediumName, smallName, criteria, brandName, dType, minPrc, maxPrc, pageable);
		AtomicInteger infoId = new AtomicInteger(0);
		AtomicInteger responseId = new AtomicInteger(0);

		// 조회된 상품 정보를 ProductInfoDto 리스트로 변환
		List<ProductInfoDto> productInfoDtos = productPage.getContent().stream()
			.map(product -> ProductInfoDto.builder()
				.id(infoId.getAndIncrement())
				.code(product.getCode())
				.build())
			.toList();

		// CategoryProductResponseDto 객체 생성 및 반환
		return CategoryProductResponseDto.builder()
			.id(responseId.getAndIncrement())
			.largeName(largeName)
			.mediumName(mediumName)
			.smallName(smallName)
			.total((int)productPage.getTotalElements())
			.curPage(productPage.getNumber())
			.lastPage(productPage.getTotalPages())
			.products(productInfoDtos)
			.build();
	}

}