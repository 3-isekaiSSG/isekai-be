package com.isekai.ssgserver.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isekai.ssgserver.member.dto.SingleProductReqDto;
import com.isekai.ssgserver.member.service.MemberFavoriteService;

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

	// 단일상품_찜하기 // 묶음상품, 카테고리L, 카테고리M, 판매자
	@PutMapping("/singleProduct")
	public ResponseEntity<?> putSingleProduct(
		@RequestBody SingleProductReqDto singleProductReqDto) {
		/* enum 으로 (단일상품, 묶음상품, 카테고리L, 카테고리M, 판매자)
		 *	product_id
		 *  division = 0
		 * */
		log.info("MemberFavoriteController.putSingleProduct");
		log.info("singleProductReqDto = " + singleProductReqDto);

		memberFavoriteService.putSingleProduct(singleProductReqDto);

		return null;
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
