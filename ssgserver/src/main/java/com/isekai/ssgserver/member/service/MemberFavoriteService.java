package com.isekai.ssgserver.member.service;

import org.springframework.stereotype.Service;

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
		/* 단일상품 찜하기 추가
		 *
		 * */
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
}
