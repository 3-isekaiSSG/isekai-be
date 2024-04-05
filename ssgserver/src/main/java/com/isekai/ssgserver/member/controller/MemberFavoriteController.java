package com.isekai.ssgserver.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isekai.ssgserver.member.dto.BundleProductReqDto;
import com.isekai.ssgserver.member.dto.CategoryLReqDto;
import com.isekai.ssgserver.member.dto.CategoryMReqDto;
import com.isekai.ssgserver.member.dto.FavoriteCountResponseDto;
import com.isekai.ssgserver.member.dto.FavoriteDelRequestDto;
import com.isekai.ssgserver.member.dto.FavoritePutReqDto;
import com.isekai.ssgserver.member.dto.SellerReqDto;
import com.isekai.ssgserver.member.dto.SingleProductReqDto;
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

	/**  뭐든 찜하기
	 *	identifier
	 */
	@PostMapping("/{identifier}/{division}")
	@Operation(summary = "뭐든 상품 찜하기", description = "회원이 찜한 것을 저장합니다.")
	public ResponseEntity<Void> FavoriteTotalAdd(
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

	/** 단일상품찜하기
	 *	product_id
	 * 	division = 0
	 */
	@PostMapping("/single-product")
	@Operation(summary = "단일 상품 찜하기", description = "회원이 단일 상품 찜한 것을 저장합니다.")
	public ResponseEntity<Void> FavoriteSingleProductAdd(
		@RequestHeader("Authorization") String token,
		@RequestBody SingleProductReqDto singleProductReqDto) {

		String uuid = jwtProvider.getUuid(token);
		memberFavoriteService.addSingleProduct(uuid, singleProductReqDto);
		return ResponseEntity.ok().build();
	}

	/** 묶음상품찜하기
	 *	bundel_id
	 * 	division = 1
	 */
	@PostMapping("/bundle-product")
	@Operation(summary = "묶음 상품 찜하기", description = "회원이 묶음 상품 찜한 것을 저장합니다.")
	public ResponseEntity<Void> FavrotieBundleProductAdd(
		@RequestHeader("Authorization") String token,
		@RequestBody BundleProductReqDto bundleProductReqDto) {

		String uuid = jwtProvider.getUuid(token);

		memberFavoriteService.addBundleProduct(uuid, bundleProductReqDto);
		return ResponseEntity.ok().build();
	}

	/** 카테고리L 찜하기
	 *	category_l_id
	 * 	division = 2
	 * */
	@PostMapping("/categoryl")
	@Operation(summary = "대 카테고리 찜하기", description = "회원이 대 카테고리 찜한 것을 저장합니다.")
	public ResponseEntity<Void> FavoriteCategoryLAdd(
		@RequestHeader("Authorization") String token,
		@RequestBody CategoryLReqDto categoryLReqDto) {

		String uuid = jwtProvider.getUuid(token);

		memberFavoriteService.addCategoryL(uuid, categoryLReqDto);
		return ResponseEntity.ok().build();
	}

	/** 카테고리M 찜하기
	 *	category_m_id
	 * 	division = 3
	 * */
	@PostMapping("/categorym")
	@Operation(summary = "중 카테고리 찜하기", description = "회원이 중 카테고리 찜한 것을 저장합니다.")
	public ResponseEntity<Void> FavoriteCategoryMAdd(
		@RequestHeader("Authorization") String token,
		@RequestBody CategoryMReqDto categoryMReqDto) {

		String uuid = jwtProvider.getUuid(token);

		memberFavoriteService.addCategoryM(uuid, categoryMReqDto);
		return ResponseEntity.ok().build();
	}

	/** 판매자,브랜드 찜하기
	 *	category_m_id
	 * 	division = 4
	 */
	@PostMapping("/seller")
	@Operation(summary = "브랜드 찜하기", description = "회원이 브랜드 찜한 것을 저장합니다.")
	public ResponseEntity<Void> FavoriteSellerAdd(
		@RequestHeader("Authorization") String token,
		@RequestBody SellerReqDto sellerReqDto) {

		String uuid = jwtProvider.getUuid(token);

		memberFavoriteService.addSeller(uuid, sellerReqDto);
		return ResponseEntity.ok().build();
	}

	/** 찜 목록 조회
	 * 	동적 - 쿼리dsl로
	 * 	--> 상품전체 / 브랜드&스토어 / 카테고리 /
	 * 	30개씩 페이지네이션
	 */
	@GetMapping("/list")
	@Operation(summary = "찜 목록 조회", description = "찜 목록을 조회합니다.")
	public ResponseEntity<?> FavoriteList() {
		return null;
	}

	@GetMapping("/check")
	@Operation(summary = "찜인지 여부", description = "찜인지 여부를 조회합니다.")
	public ResponseEntity<Boolean> FavoriteIsDetails(
		@RequestHeader("Authorization") String token,
		@RequestParam(value = "division") byte division,
		@RequestParam(value = "identifier") String identifier
	) {
		String uuid = jwtProvider.getUuid(token);

		boolean result = memberFavoriteService.checkFavoriteExists(uuid, division, identifier);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/number")
	@Operation(summary = "찜 갯수", description = "찜 상품, 브랜드, 카테고리 갯수를 구합니다.")
	public ResponseEntity<FavoriteCountResponseDto> FavoriteCountList() {
		FavoriteCountResponseDto favoriteCountResponseDto = memberFavoriteService.countFavorites();

		return ResponseEntity.ok(favoriteCountResponseDto);
	}

	@DeleteMapping("/selects")
	@Operation(summary = "찜 선택들 삭제", description = "찜 선택한거 모두 삭제합니다.")
	public ResponseEntity<Void> FavoriteListRemove(
		@RequestBody FavoriteDelRequestDto favoriteDelRequestDto) {

		memberFavoriteService.removeFavorites(favoriteDelRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{favoriteId}")
	@Operation(summary = "찜 하나 삭제", description = "찜 하나를 삭제합니다.")
	public ResponseEntity<Void> FavoriteRemove(
		@PathVariable Long favoriteId) {

		memberFavoriteService.removeFavoriteOne(favoriteId);
		return ResponseEntity.ok().build();
	}

}
