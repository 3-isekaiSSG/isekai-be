package com.isekai.ssgserver.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.dto.BundleProductReqDto;
import com.isekai.ssgserver.member.dto.CategoryLReqDto;
import com.isekai.ssgserver.member.dto.CategoryMReqDto;
import com.isekai.ssgserver.member.dto.FavoriteDelReqDto;
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
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void putSingleProduct(SingleProductReqDto singleProductReqDto) {
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

	public void putBundleProduct(BundleProductReqDto bundleProductReqDto) {
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

	public void putCategoryL(CategoryLReqDto categoryLReqDto) {
		String uuid = categoryLReqDto.getUuid();
		Long identifier = categoryLReqDto.getCategory_l_id();
		FavoriteDivision categoryL = FavoriteDivision.CATEGORYL;
		byte division = categoryL.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	public void putCategoryM(CategoryMReqDto categoryMReqDto) {
		String uuid = categoryMReqDto.getUuid();
		Long identifier = categoryMReqDto.getCategory_m_id();
		FavoriteDivision categoryM = FavoriteDivision.CATEGORYM;
		byte division = categoryM.getCode();

		Favorite favorite = Favorite.builder()
			.uuid(uuid)
			.division(division)
			.identifier(identifier)
			.build();

		memberFavoriteRepository.save(favorite);
	}

	public void putSeller(SellerReqDto sellerReqDto) {
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

	public void deleteFavoriteOne(FavoriteDelReqDto favoriteDelReqDto) {
		Long favoriteId = favoriteDelReqDto.getFavorite_id();
		Favorite favoriteOptional = memberFavoriteRepository.findById(favoriteId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		memberFavoriteRepository.deleteById(favoriteId);
	}

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
