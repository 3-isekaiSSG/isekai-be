package com.isekai.ssgserver.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.category.dto.CategoryResponseDto;
import com.isekai.ssgserver.category.service.CategoryService;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Category", description = "각 카테고리 조회 API document")
public class CategoryController {

	// @Autowired
	private final CategoryService categoryService;

	// public CategoryController(CategoryService categoryService) {
	// 	this.categoryService = categoryService;
	// }

	// 카테고리 대,중분류
	@GetMapping("/category")
	@Operation(summary = "카테고리 대,중분류 이름", description = "하단 카테고리 클릭시 나오는 대,중분류 이름 데이터를 내려줍니다.")
	public ResponseEntity<List<CategoryResponseDto>> getCategory() {

		try {
			List<CategoryResponseDto> categoryResponseDto = categoryService.getCategory();
			return new ResponseEntity<>(categoryResponseDto, HttpStatus.OK);
		} catch (Exception exception) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		}
	}
}