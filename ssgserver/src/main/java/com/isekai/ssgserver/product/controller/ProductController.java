package com.isekai.ssgserver.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.product.dto.CategoryProductResponseDto;
import com.isekai.ssgserver.product.dto.DiscountDto;
import com.isekai.ssgserver.product.dto.ProductDetailDto;
import com.isekai.ssgserver.product.dto.ProductSummaryDto;
import com.isekai.ssgserver.product.dto.ReviewScoreDto;
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

	// 카테고리 상품 조회
	@GetMapping
	@Operation(summary = "카테고리 상품 리스트", description = "카테고리 별 상품 리스트를 내려주고, 정렬과 필터링 가능합니다.")
	public ResponseEntity<CategoryProductResponseDto> getCategoryProduct(
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
		String modifiedSmallName = (smallName != null) ? smallName.replace('-', '/') : null;

		CategoryProductResponseDto categoryProductResponse = productService.getCategoryProduct(modifiedLargeName,
			modifiedMediumName, modifiedSmallName, index, criteria, brandName, dType, minPrc, maxPrc);
		return ResponseEntity.ok(categoryProductResponse);
	}

	@GetMapping("/{productCode}")
	@Operation(summary = "상품 데이터 조회 - 카드 형식", description = "홈, 카테고리, 찜 등등 페이지에서 카드 형식의 상품 표시를 위해 사용됩니다.")
	public ResponseEntity<ProductSummaryDto> getProductSummary(@PathVariable String productCode) {
		ProductSummaryDto productSummaryDto = productService.getProductInfo(productCode);
		return ResponseEntity.ok(productSummaryDto);
	}

	@GetMapping("/{productCode}/detail")
	@Operation(summary = "상품 상세 조회", description = "상품 상세 페이지에서 사용되는 자세한 정보입니다.")
	public ResponseEntity<ProductDetailDto> getProductDetail(@PathVariable String productCode) {
		ProductDetailDto productDetailDto = productService.getProductDetail(productCode);
		return ResponseEntity.ok(productDetailDto);
	}

	@GetMapping("/{productCode}/discount")
	@Operation(summary = "상품 할인 조회 - 단일 상품", description = "상품코드로 할인율, 할인가 조회")
	public ResponseEntity<DiscountDto> getDiscount(@PathVariable String productCode) {
		DiscountDto discountDto = productService.getDiscountByProduct(productCode);
		return ResponseEntity.ok(discountDto);
	}

	@GetMapping("/{productCode}/review-score")
	@Operation(summary = "상품 리뷰 집계 조회 - 단일 상품", description = "상품코드로 할인율, 할인가 조회")
	public ResponseEntity<ReviewScoreDto> getReviewScore(@PathVariable String productCode) {
		ReviewScoreDto reviewScoreDto = productService.getReviewScoreByProduct(productCode);
		return ResponseEntity.ok(reviewScoreDto);

	}

}

