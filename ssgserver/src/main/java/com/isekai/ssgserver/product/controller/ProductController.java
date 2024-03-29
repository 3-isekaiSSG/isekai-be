package com.isekai.ssgserver.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.product.dto.CategoryProductResponseDto;
import com.isekai.ssgserver.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product", description = "상품 조회 API document")
public class ProductController {

	private final ProductService productService;

	// return 값 명시 수정하기
	@GetMapping
	@Operation(summary = "카테고리 상품 리스트", description = "카테고리 별 상품 리스트를 내려주고, 정렬과 필터링 가능합니다.")
	public ResponseEntity<?> getCategoryProduct(
		@RequestParam(value = "largeName") String largeName,
		@RequestParam(value = "mediumName") String mediumName,
		@RequestParam(value = "smallName", required = false) String smallName,
		@RequestParam(value = "page", required = false, defaultValue = "0") Integer index,
		@RequestParam(value = "sort", required = false) String criteria,
		@RequestParam(value = "brandName", required = false) String brandName,
		@RequestParam(value = "dType", required = false) String dType,
		@RequestParam(value = "minPrc", required = false) Integer minPrc,
		@RequestParam(value = "maxPrc", required = false) Integer maxPrc
	) {

		String modifiedLargeName = largeName.replace('-', '/');
		String modifiedMediumName = mediumName.replace('-', '/');
		String modifiedSmallName = smallName.replace('-', '/');

		CategoryProductResponseDto categoryProductResponse = productService.getCategoryProduct(modifiedLargeName,
			modifiedMediumName, modifiedSmallName, index, criteria, brandName, dType, minPrc, maxPrc);
		return ResponseEntity.ok(categoryProductResponse);
	}
}
