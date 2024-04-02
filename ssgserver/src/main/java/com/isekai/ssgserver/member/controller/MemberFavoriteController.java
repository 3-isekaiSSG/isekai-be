package com.isekai.ssgserver.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.member.dto.BundleProductReqDto;
import com.isekai.ssgserver.member.dto.CategoryLReqDto;
import com.isekai.ssgserver.member.dto.CategoryMReqDto;
import com.isekai.ssgserver.member.dto.SellerReqDto;
import com.isekai.ssgserver.member.dto.SingleProductReqDto;
import com.isekai.ssgserver.member.service.MemberFavoriteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/favorite")
@Slf4j
@Tag(name = "favorite", description = "회원 상품 좋아요 API document")
public class MemberFavoriteController {
	private final MemberFavoriteService memberFavoriteService;

	/* 단일상품찜하기
	 *	product_id
	 * 	division = 0
	 * */
	@PutMapping("/singleProduct")
	@Operation(summary = "단일 상품 찜하기", description = "회원이 단일 상품 찜한 것을 저장합니다.")
	public ResponseEntity<?> putSingleProduct(
		@RequestBody SingleProductReqDto singleProductReqDto) {

		log.info("MemberFavoriteController.putSingleProduct");
		log.info("singleProductReqDto = " + singleProductReqDto);

		memberFavoriteService.putSingleProduct(singleProductReqDto);
		return ResponseEntity.ok().build();
	}

	/* 묶음상품찜하기
	 *	bundel_id
	 * 	division = 1
	 * */
	@PutMapping("/bundleProduct")
	@Operation(summary = "묶음 상품 찜하기", description = "회원이 묶음 상품 찜한 것을 저장합니다.")
	public ResponseEntity<?> putBundleProduct(
		@RequestBody BundleProductReqDto bundleProductReqDto) {

		log.info("MemberFavoriteController.putBundleProduct");
		log.info("bundleProductReqDto = " + bundleProductReqDto);

		memberFavoriteService.putBundleProduct(bundleProductReqDto);
		return ResponseEntity.ok().build();
	}

	/* 카테고리L 찜하기
	 *	category_l_id
	 * 	division = 2
	 * */
	@PutMapping("/categoryL")
	@Operation(summary = "대 카테고리 찜하기", description = "회원이 대 카테고리 찜한 것을 저장합니다.")
	public ResponseEntity<?> putCategoryL(
		@RequestBody CategoryLReqDto categoryLReqDto) {
		log.info("MemberFavoriteController.putCategoryL");
		log.info("categoryLReqDto = " + categoryLReqDto);

		memberFavoriteService.putCategoryL(categoryLReqDto);
		return ResponseEntity.ok().build();
	}

	/* 카테고리M 찜하기
	 *	category_m_id
	 * 	division = 3
	 * */
	@PutMapping("/categoryM")
	@Operation(summary = "중 카테고리 찜하기", description = "회원이 중 카테고리 찜한 것을 저장합니다.")
	public ResponseEntity<?> putCategoryM(
		@RequestBody CategoryMReqDto categoryMReqDto) {
		log.info("MemberFavoriteController.putCategoryM");
		log.info("categoryMReqDto = " + categoryMReqDto);

		memberFavoriteService.putCategoryM(categoryMReqDto);
		return ResponseEntity.ok().build();
	}

	/* 판매자,브랜드 찜하기
	 *	category_m_id
	 * 	division = 4
	 * */
	@PutMapping("/seller")
	@Operation(summary = "브랜드 찜하기", description = "회원이 브랜드 찜한 것을 저장합니다.")
	public ResponseEntity<?> putSeller(
		@RequestBody SellerReqDto sellerReqDto) {
		log.info("MemberFavoriteController.putSeller");
		log.info("sellerReqDto = " + sellerReqDto);

		memberFavoriteService.putSeller(sellerReqDto);
		return ResponseEntity.ok().build();
	}

	// 찜 목록 조회
	@GetMapping("/list")
	public ResponseEntity<?> getFavoriteList() {
		return null;
	}

	// 찜인지
	@GetMapping("/{id}")
	public ResponseEntity<?> getIsFavorite() {
		return null;
	}

	// 찜 갯수 조회
	@GetMapping("/number")
	public ResponseEntity<?> getCountFavorite() {
		return null;
	}

	// 찜 선택 삭제
	@DeleteMapping("/selects")
	public ResponseEntity<?> deleteFavoriteList() {
		return null;
	}

	// 찜 1개 삭제
	@DeleteMapping("/{favorite_id}")
	public ResponseEntity<?> deleteFavoriteSeelct() {
		return null;
	}

}
