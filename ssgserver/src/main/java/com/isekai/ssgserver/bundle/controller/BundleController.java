package com.isekai.ssgserver.bundle.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.bundle.dto.BundleListResDto;
import com.isekai.ssgserver.bundle.service.BundleService;

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

	/**
	 * 묶음상품 리스트
	 *
	 * @return list 묶음상품 code 값
	 */
	@GetMapping("")
	public ResponseEntity<Page<BundleListResDto>> getBundleList(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int pageSize
	) {
		Page<BundleListResDto> bundleList = bundleService.getBundleList(page, pageSize);
		return ResponseEntity.ok(bundleList);
	}

	/**
	 * 묶음상품 카드
	 */
	@GetMapping("/{code}")
	public ResponseEntity<?> getBundleCode(
		@PathVariable Integer code
	) {
		return ResponseEntity.ok().build();
	}

	/**
	 * 묶음상품 하나 정보
	 * @return
	 */
	@GetMapping("/{bunle}")
	public ResponseEntity<?> getBundleDetails() {
		return ResponseEntity.ok().build();
	}

	/**
	 * 묶음 상품 세부 상품 리스트
	 */
	@GetMapping("/{bundle}/list")
	public ResponseEntity<?> getBundleProductsList() {
		return ResponseEntity.ok().build();
	}

}
