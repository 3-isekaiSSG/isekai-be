package com.isekai.ssgserver.seller.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.seller.dto.BrandNameResponseDto;
import com.isekai.ssgserver.seller.dto.BrandSortOptionResponseDto;
import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.service.SellerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
@Tag(name = "Seller", description = "판매자 조회 API document")
public class SellerController {

	private final SellerService sellerService;

	@GetMapping("/products/{productCode}")
	@Operation(summary = "판매자 조회 - 단일 상품", description = "상품코드로 판매자 조회")
	public ResponseEntity<SellerDto> getSellerByProduct(@PathVariable String productCode) {
		SellerDto sellerDto = sellerService.getSellerByProduct(productCode);
		return ResponseEntity.ok(sellerDto);
	}

	// 카테고리 상품 조회시 필터링에 쓰이는 브랜드이름
	@GetMapping("/products")
	@Operation(summary = "브랜드이름 및 상품수", description = "카테고리 상품 조회시 필터링에 사용되는 브랜드이름과 상품수")
	public ResponseEntity<List<BrandNameResponseDto>> getBrandNameForFilter(
		@RequestParam(value = "largeName") String largeName,
		@RequestParam(value = "mediumName") String mediumName,
		@RequestParam(value = "smallName", required = false) String smallName,
		@RequestParam(value = "sort", required = false) String criteria) {

		String modifiedLargeName = largeName.replace('-', '/');
		String modifiedMediumName = mediumName.replace('-', '/');
		String modifiedSmallName = (smallName != null) ? smallName.replace('-', '/') : null;

		List<BrandNameResponseDto> brandNameResponse = sellerService.getSellerByProduct(modifiedLargeName,
			modifiedMediumName, modifiedSmallName, criteria);

		return ResponseEntity.ok(brandNameResponse);
	}

	// 필터링 브랜드 이름 정렬
	@GetMapping("/sort")
	@Operation(summary = "브랜드 이름 정렬 목록", description = "브랜드 이름 필터링 정렬 목록")
	public ResponseEntity<List<BrandSortOptionResponseDto>> getBrandSortOption() {

		List<BrandSortOptionResponseDto> brandSortOptionResponse = sellerService.getBrandSortOption();
		return ResponseEntity.ok(brandSortOptionResponse);
	}
}
