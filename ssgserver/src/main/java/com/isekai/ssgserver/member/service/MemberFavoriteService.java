package com.isekai.ssgserver.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryLRepository;
import com.isekai.ssgserver.category.repository.CategoryMRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.BundleProductReqDto;
import com.isekai.ssgserver.member.dto.CategoryLReqDto;
import com.isekai.ssgserver.member.dto.CategoryMReqDto;
import com.isekai.ssgserver.member.dto.FavoriteDelReqDto;
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
	public void postFavoriteTotal(FavoritePutReqDto favoritePutReqDto) {
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
	public void postSingleProduct(SingleProductReqDto singleProductReqDto) {
		String uuid = singleProductReqDto.getUuid();
		Long identifier = singleProductReqDto.getProduct_id();
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
	public void postBundleProduct(BundleProductReqDto bundleProductReqDto) {
		String uuid = bundleProductReqDto.getUuid();
		Long identifier = bundleProductReqDto.getBundel_id();
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
	public void postCategoryL(CategoryLReqDto categoryLReqDto) {
		String uuid = categoryLReqDto.getUuid();
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
	public void postCategoryM(CategoryMReqDto categoryMReqDto) {
		String uuid = categoryMReqDto.getUuid();
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
	public void postSeller(SellerReqDto sellerReqDto) {
		String uuid = sellerReqDto.getUuid();
		Long identifier = sellerReqDto.getSeller_id();
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
	public void deleteFavoriteOne(FavoriteDelReqDto favoriteDelReqDto) {
		Long favoriteId = favoriteDelReqDto.getFavorite_id();
		Favorite favoriteOptional = memberFavoriteRepository.findById(favoriteId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		memberFavoriteRepository.deleteById(favoriteId);
	}

	@Transactional
	public void deleteFavorites(List<Long> favoriteIds) {
		for (Long favoriteId : favoriteIds) {
			memberFavoriteRepository.findById(favoriteId)
				.ifPresentOrElse(
					favorite -> memberFavoriteRepository.deleteById(favoriteId),
					() -> {
						throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
					}
				);
		}
	}

}
