package com.isekai.ssgserver.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.product.dto.ProductMResponseDto;
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

	// 카테고리 중분류 상품 조회
	@GetMapping("/medium/{categoryMId}")
	@Operation(summary = "카테고리 중분류 상품 조회", description = "카테고리 중분류별 상품 데이터 입니다.")
	public ResponseEntity<ProductMResponseDto> getCategoryMProduct(@PathVariable Long categoryMId,
		@RequestParam(required = false, defaultValue = "0", value = "page") int index,
		@RequestParam(required = false, value = "sort") String sortCriteria) {

		try {
			ProductMResponseDto productsM = productService.getProductsM(categoryMId, index);
			return new ResponseEntity<>(productsM, HttpStatus.OK);
		} catch (CustomException exception) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		}
	}
}
