package com.isekai.ssgserver.bundle.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.bundle.dto.BundleCardResDto;
import com.isekai.ssgserver.bundle.dto.BundleInfoResDto;
import com.isekai.ssgserver.bundle.dto.BundleListResDto;
import com.isekai.ssgserver.bundle.dto.BundleProductListResDto;
import com.isekai.ssgserver.bundle.service.BundleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bundles")
@Tag(name = "Bundle", description = "묶음상품 API document")
public class BundleController {
	private final BundleService bundleService;

	@GetMapping("")
	@Operation(summary = "묶음 상품 목록 조회", description = "묶음 상품 리스트를 조회 합니다.")
	public ResponseEntity<Page<BundleListResDto>> getBundleList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int pageSize
	) {
		Page<BundleListResDto> bundleList = bundleService.getBundleList(page, pageSize);
		return ResponseEntity.ok(bundleList);
	}

	@GetMapping("/card/{code}")
	@Operation(summary = "묶음 상품 카드 조회", description = "묶음 상품 카드 정보를 조회 합니다.")
	public ResponseEntity<BundleCardResDto> getBundleCode(
		@PathVariable String code
	) {
		BundleCardResDto bundleCard = bundleService.getBudleCardInfo(code);
		return ResponseEntity.ok(bundleCard);
	}

	@GetMapping("/{code}/info")
	@Operation(summary = "묶음 상품 내부 조회", description = "묶음 상품 내부 정보를 조회 합니다.")
	public ResponseEntity<BundleInfoResDto> getBundleDetails(
		@PathVariable String code
	) {
		BundleInfoResDto bundleInfo = bundleService.getBundleDetails(code);
		return ResponseEntity.ok(bundleInfo);
	}

	@GetMapping("/{code}/list")
	@Operation(summary = "묶음 상품 내부 세부 상품 목록 조회", description = "묶음 상품 내부 세부 상품 목록 정보를 조회 합니다.")
	public ResponseEntity<List<BundleProductListResDto>> getBundleProductsList(
		@PathVariable String code
	) {
		List<BundleProductListResDto> bundleProductList = bundleService.getBundleProductList(code);
		return ResponseEntity.ok(bundleProductList);
	}
}
