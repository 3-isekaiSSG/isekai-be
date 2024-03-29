package com.isekai.ssgserver.product.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryProductCustomRepository;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.image.repository.ImageRepository;
import com.isekai.ssgserver.product.dto.CategoryProductResponseDto;
import com.isekai.ssgserver.product.repository.DiscountRepository;
import com.isekai.ssgserver.product.repository.ProductRepository;
import com.isekai.ssgserver.review.repository.ReviewScoreRepository;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

	// private final CategoryProductRepository categoryProductRepository;
	private final CategoryProductCustomRepository categoryProductCustomRepository;
	private final ReviewScoreRepository reviewScoreRepository;
	private final DiscountRepository discountRepository;
	private final SellerProductRepository sellerProductRepository;
	private final ProductDeliveryTypeRepository productDeliveryTypeRepository;
	private final ProductRepository productRepository;
	private final ImageRepository imageReposiroty;

	// 카테고리 상품 조회
	public CategoryProductResponseDto getCategoryProduct(String largeName, String mediumName, String smallName,
		Integer index, String criteria, String brandName, String dType, Integer minPrc, Integer maxPrc) {

		// pageable 객체 생성
		Pageable pageable = PageRequest.of(index, 40);

		return null;

	}

}