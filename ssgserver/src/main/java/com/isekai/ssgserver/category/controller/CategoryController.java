package com.isekai.ssgserver.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.category.dto.CategoryLMSDto;
import com.isekai.ssgserver.category.dto.CategoryLResponseDto;
import com.isekai.ssgserver.category.dto.CategoryMResponseDto;
import com.isekai.ssgserver.category.dto.CategorySResponseDto;
import com.isekai.ssgserver.category.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Category", description = "각 카테고리 조회 API document")
public class CategoryController {

	private final CategoryService categoryService;

	// 카테고리 대분류 조회
	@GetMapping("/large")
	@Operation(summary = "카테고리 대분류 조회", description = "대분류만 내려줍니다.")
	public ResponseEntity<List<CategoryLResponseDto>> getCategoryL() {

		List<CategoryLResponseDto> categoryLResponseDto = categoryService.getCategoryL();
		return new ResponseEntity<>(categoryLResponseDto, HttpStatus.OK);

	}

	// 카테고리 중분류 조회
	@GetMapping("/medium/{largeName}")
	@Operation(summary = "카테고리 중분류 조회", description = "대분류 상품 전체보기 클릭시 나오는 중분류 이름 데이터를 내려줍니다.")
	public ResponseEntity<CategoryMResponseDto> getCategoryM(@PathVariable String largeName) {

		String modifiedLargeName = largeName.replace('-', '/');  // largeName에 '-' 있으면 '/' 로 바꿔주는 작업

		CategoryMResponseDto categoryMResponseDto = categoryService.getCategoryM(modifiedLargeName);
		return new ResponseEntity<>(categoryMResponseDto, HttpStatus.OK);
	}

	// 카테고리 소분류 조회
	@GetMapping("/small/{mediumName}")
	@Operation(summary = "카테고리 소분류 조회", description = "중분류 상품 조회시 나오는 소분류 이름 데이터를 내려줍니다.")
	public ResponseEntity<CategorySResponseDto> getCategoryS(@PathVariable String mediumName) {

		String modifiedMediumName = mediumName.replace('-', '/');

		CategorySResponseDto categorySResponseDto = categoryService.getCategoryS(modifiedMediumName);
		return new ResponseEntity<>(categorySResponseDto, HttpStatus.OK);

	}

	@GetMapping("/products/{productCode}")
	@Operation(summary = "카테고리 대,중,소 조회 - 단일 상품", description = "상품 상세페이지 최하단 카테고리 표시")
	public ResponseEntity<CategoryLMSDto> getCategoryByProduct(@PathVariable String productCode) {
		CategoryLMSDto categoryLMSDto = categoryService.getCategoryByProduct(productCode);
		return ResponseEntity.ok(categoryLMSDto);
	}
}