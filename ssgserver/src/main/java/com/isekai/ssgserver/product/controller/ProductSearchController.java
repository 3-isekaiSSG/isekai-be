package com.isekai.ssgserver.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.product.dto.ProductInfoDto;
import com.isekai.ssgserver.product.service.ProductSearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products/search")
@Tag(name = "ProductSearch", description = "상품 검색 관련 API document")
public class ProductSearchController {

	private final ProductSearchService productSearchService;

	@GetMapping("/general")
	@Operation(summary = "상품 검색", description = "검색창에 키워드 이름을 검색하면 조회됩니다.")
	public ResponseEntity<List<ProductInfoDto>> generalSearchProduct(@RequestParam(name = "keyword") String keyword) {

		List<ProductInfoDto> products = productSearchService.generalSearchProduct(keyword);

		return ResponseEntity.ok(products);
	}
}
