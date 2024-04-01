package com.isekai.ssgserver.option.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.option.dto.OptionDepthDto;
import com.isekai.ssgserver.option.service.OptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/options")
@Tag(name = "Option", description = "옵션 조회 API document")
public class OptionController {

	private final OptionService optionService;

	@GetMapping("/products/{productCode}")
	@Operation(summary = "옵션 category 전체 조회 - 단일상품", description = "상품에 종속된 옵션의 모든 계층 이름만 조회합니다. ex)사이즈, 색상 ..")
	public ResponseEntity<List<OptionDepthDto>> getAllOptionCategoryByProduct(@PathVariable String productCode) {
		List<OptionDepthDto> optionDepthDtoList = optionService.getAllOptionCategoryByProduct(productCode);
		return ResponseEntity.ok(optionDepthDtoList);
	}
}
