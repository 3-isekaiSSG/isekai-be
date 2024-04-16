package com.isekai.ssgserver.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isekai.ssgserver.member.dto.FavoriteCountResponseDto;
import com.isekai.ssgserver.member.dto.FavoriteDelRequestDto;
import com.isekai.ssgserver.member.dto.FavoritePutReqDto;
import com.isekai.ssgserver.member.dto.FavoriteReqDto;
import com.isekai.ssgserver.member.dto.FavoriteResDto;
import com.isekai.ssgserver.member.enums.FavoriteDivision;
import com.isekai.ssgserver.member.service.MemberFavoriteService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

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
	private final JwtProvider jwtProvider;

	@PostMapping("/{identifier}/{division}")
	@Operation(summary = "뭐든 상품 찜하기", description = "회원이 찜한 것을 저장합니다.")
	public ResponseEntity<Void> addFavoriteTotal(
		@RequestHeader("Authorization") String token,
		@PathVariable String identifier,
		@PathVariable FavoriteDivision division) {
		String uuid = jwtProvider.getUuid(token);

		FavoritePutReqDto favoritePutReqDto = FavoritePutReqDto.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();
		memberFavoriteService.addFavoriteTotal(favoritePutReqDto);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/product-list")
	@Operation(summary = "찜 상품 목록 조회", description = "찜 상품 목록을 조회합니다.")
	public ResponseEntity<Page<FavoriteResDto>> getFavoriteProductList(
		@RequestHeader("Authorization") String token,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize
	) {
		String uuid = jwtProvider.getUuid(token);

		Page<FavoriteResDto> productList = memberFavoriteService.getFavoriteProductList(uuid, page, pageSize);

		return ResponseEntity.ok(productList);
	}

	@GetMapping("/seller-list")
	@Operation(summary = "찜 브랜드 목록 조회", description = "찜 목록을 조회합니다.")
	public ResponseEntity<Page<FavoriteResDto>> getFavoriteSellerList(
		@RequestHeader("Authorization") String token,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize
	) {
		String uuid = jwtProvider.getUuid(token);
		Page<FavoriteResDto> sellerList = memberFavoriteService.getFavoriteSellerList(uuid, page, pageSize);
		return ResponseEntity.ok(sellerList);
	}

	@GetMapping("/category-list")
	@Operation(summary = "찜 카테고리 목록 조회", description = "찜 카테고리 목록을 조회합니다.")
	public ResponseEntity<Page<FavoriteResDto>> getFavoriteCategoryList(
		@RequestHeader("Authorization") String token,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int pageSize
	) {
		String uuid = jwtProvider.getUuid(token);
		Page<FavoriteResDto> categoryList = memberFavoriteService.getFavoriteCategoryList(uuid, page, pageSize);
		return ResponseEntity.ok(categoryList);
	}

	@GetMapping("/check/{identifier}/{division}")
	@Operation(summary = "찜인지 여부", description = "찜인지 여부를 조회합니다.")
	public ResponseEntity<Boolean> getFavoriteIsDetails(
		@RequestHeader("Authorization") String token,
		@PathVariable String identifier,
		@PathVariable FavoriteDivision division
	) {
		String uuid = jwtProvider.getUuid(token);
		FavoriteReqDto favoriteReqDto = FavoriteReqDto.builder()
			.identifier(identifier)
			.division(division)
			.build();

		boolean result = memberFavoriteService.getFavoriteIsDetails(uuid, favoriteReqDto);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/number")
	@Operation(summary = "찜 갯수", description = "찜 상품, 브랜드, 카테고리 갯수를 구합니다.")
	public ResponseEntity<FavoriteCountResponseDto> getFavoriteCountList(
		@RequestHeader("Authorization") String token
	) {
		String uuid = jwtProvider.getUuid(token);
		FavoriteCountResponseDto favoriteCountResponseDto = memberFavoriteService.getFavoriteCountList(uuid);

		return ResponseEntity.ok(favoriteCountResponseDto);
	}

	@PutMapping("/selects")
	@Operation(summary = "찜 선택들 삭제", description = "찜 선택한거 모두 삭제합니다.")
	public ResponseEntity<Void> removeFavoriteList(
		@RequestHeader("Authorization") String token,
		@RequestBody FavoriteDelRequestDto favoriteDelRequestDto) {

		String uuid = jwtProvider.getUuid(token);
		memberFavoriteService.removeFavoriteList(uuid, favoriteDelRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{identifier}/{division}")
	@Operation(summary = "찜 하나 삭제", description = "찜 하나를 삭제합니다.")
	public ResponseEntity<Void> removeFavoriteOne(
		@RequestHeader("Authorization") String token,
		@PathVariable String identifier,
		@PathVariable FavoriteDivision division
	) {
		String uuid = jwtProvider.getUuid(token);
		FavoriteReqDto favoriteReqDto = FavoriteReqDto.builder()
			.identifier(identifier)
			.division(division)
			.build();

		memberFavoriteService.removeFavoriteOne(uuid, favoriteReqDto);
		return ResponseEntity.ok().build();
	}

}
