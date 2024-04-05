package com.isekai.ssgserver.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryLRepository;
import com.isekai.ssgserver.category.repository.CategoryMRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.BundleProductReqDto;
import com.isekai.ssgserver.member.dto.CategoryLReqDto;
import com.isekai.ssgserver.member.dto.CategoryMReqDto;
import com.isekai.ssgserver.member.dto.FavoriteCountDto;
import com.isekai.ssgserver.member.dto.FavoriteCountResponseDto;
import com.isekai.ssgserver.member.dto.FavoriteDelReqDto;
import com.isekai.ssgserver.member.dto.FavoriteDelRequestDto;
import com.isekai.ssgserver.member.dto.FavoritePutReqDto;
import com.isekai.ssgserver.member.dto.SellerReqDto;
import com.isekai.ssgserver.member.dto.SingleProductReqDto;
import com.isekai.ssgserver.member.entity.Favorite;
import com.isekai.ssgserver.member.enums.FavoriteDivision;
import com.isekai.ssgserver.member.repository.MemberFavoriteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberFavoriteService {
	private final MemberFavoriteRepository memberFavoriteRepository;
	private final CategoryLRepository categoryLRepository;
	private final CategoryMRepository categoryMRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void addFavoriteTotal(FavoritePutReqDto favoritePutReqDto) {
		byte division = favoritePutReqDto.getDivision().getCode();

		if (division == 2) {
			// 카테고리L (2)
			String uuid = favoritePutReqDto.getUuid();
			String largeName = favoritePutReqDto.getIdentifier();

			String modifiedLargeName = largeName.replace('-', '/');
			Long identifier = categoryLRepository.findByLargeName(modifiedLargeName);

			Favorite favorite = Favorite.builder()
				.uuid(uuid)
				.division(division)
				.identifier(identifier)
				.build();

			memberFavoriteRepository.save(favorite);

		} else if (division == 3) {
			// 카테고리M (3)
			String uuid = favoritePutReqDto.getUuid();
			String mediumName = favoritePutReqDto.getIdentifier();

			String modifiedMediumName = mediumName.replace('-', '/');
			Long identifier = categoryMRepository.findByMediumName(modifiedMediumName);

			Favorite favorite = Favorite.builder()
				.uuid(uuid)
				.division(division)
				.identifier(identifier)
				.build();

			memberFavoriteRepository.save(favorite);

		} else {
			// 나머지 ( 단일상품, 묶음상품, 브랜드 )
			String uuid = favoritePutReqDto.getUuid();
			String tempId = favoritePutReqDto.getIdentifier();

			long identifier = Long.parseLong(tempId);

			Favorite favorite = Favorite.builder()
				.uuid(uuid)
				.division(division)
				.identifier(identifier)
				.build();

			memberFavoriteRepository.save(favorite);
		}

	}

	@Transactional
	public void addSingleProduct(String uuid, SingleProductReqDto singleProductReqDto) {
		Long identifier = singleProductReqDto.getProductId();
		FavoriteDivision singleProduct = FavoriteDivision.SINGLE_PRODUCT;
		byte division = singleProduct.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	@Transactional
	public void addBundleProduct(String uuid, BundleProductReqDto bundleProductReqDto) {
		Long identifier = bundleProductReqDto.getBundelId();
		FavoriteDivision bundleProduct = FavoriteDivision.BUNDLE_PRODUCT;
		byte division = bundleProduct.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	@Transactional
	public void addCategoryL(String uuid, CategoryLReqDto categoryLReqDto) {
		String largeName = categoryLReqDto.getLargeName();
		String modifiedLargeName = largeName.replace('-', '/');
		Long identifier = categoryLRepository.findByLargeName(modifiedLargeName);

		FavoriteDivision categoryL = FavoriteDivision.CATEGORYL;
		byte division = categoryL.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	@Transactional
	public void addCategoryM(String uuid, CategoryMReqDto categoryMReqDto) {
		String medinumName = categoryMReqDto.getMediumName();
		String modifiedMediumName = medinumName.replace('-', '/');
		Long identifier = categoryMRepository.findByMediumName(modifiedMediumName);

		FavoriteDivision categoryM = FavoriteDivision.CATEGORYM;
		byte division = categoryM.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	@Transactional
	public void addSeller(String uuid, SellerReqDto sellerReqDto) {
		Long identifier = sellerReqDto.getSellerId();
		FavoriteDivision seller = FavoriteDivision.BRAND;
		byte division = seller.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	@Transactional
	public void removeFavoriteOne(Long favoriteId) {
		Favorite favoriteOptional = memberFavoriteRepository.findById(favoriteId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		memberFavoriteRepository.deleteById(favoriteId);
	}

	@Transactional
	public void removeFavoriteList(FavoriteDelRequestDto favoriteDelRequestDto) {
		List<FavoriteDelReqDto> favoriteDelList = favoriteDelRequestDto.getFavoriteDelList();

		for (FavoriteDelReqDto favoriteDelReqDto : favoriteDelList) {
			Long favoriteId = favoriteDelReqDto.getFavoriteId();
			memberFavoriteRepository.findById(favoriteId)
				.ifPresentOrElse(
					favorite -> memberFavoriteRepository.deleteById(favoriteId),
					() -> {
						throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
					}
				);
		}
	}

	@Transactional
	public FavoriteCountResponseDto getFavoriteCountList() {
		// 상품 전체 및 묶음상품 갯수
		Long totalCount = memberFavoriteRepository.countByDivisionEqualsOrDivisionEquals((byte)0, (byte)1);
		// 브랜드 갯수
		byte brandCount = memberFavoriteRepository.countByDivision((byte)4);
		// 카테고리 L, M 갯수
		Long categoryCount = memberFavoriteRepository.countByDivisionEqualsOrDivisionEquals((byte)2, (byte)3);

		return FavoriteCountResponseDto.builder()
			.favoriteCountList(new ArrayList<FavoriteCountDto>() {{
				add(new FavoriteCountDto((byte)1, totalCount));
				add(new FavoriteCountDto((byte)2, (long)brandCount));
				add(new FavoriteCountDto((byte)3, categoryCount));
			}})
			.build();
	}

	public boolean getFavoriteIsDetails(String uuid, byte division, String identifier) {
		Long identifierModify;

		if (division == 2) {
			String modifiedLargeName = identifier.replace('-', '/');
			identifierModify = categoryLRepository.findByLargeName(modifiedLargeName);
		} else if (division == 3) {
			String modifiedMediumName = identifier.replace('-', '/');
			identifierModify = categoryMRepository.findByMediumName(modifiedMediumName);
		} else {
			identifierModify = Long.parseLong(identifier);
		}

		return memberFavoriteRepository.existsByUuidAndDivisionAndIdentifier(uuid, division, identifierModify);
	}
}
