package com.isekai.ssgserver.product.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryProductRepository;
import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.delivery.entity.DeliveryType;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.product.dto.DiscountDto;
import com.isekai.ssgserver.product.dto.ProductDto;
import com.isekai.ssgserver.product.dto.ProductMResponseDto;
import com.isekai.ssgserver.product.entity.Discount;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.repository.DiscountRepository;
import com.isekai.ssgserver.review.dto.ReviewScoreDto;
import com.isekai.ssgserver.review.entity.ReviewScore;
import com.isekai.ssgserver.review.repository.ReviewScoreRepository;
import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.entity.Seller;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

	private final CategoryProductRepository categoryProductRepository;
	private final ReviewScoreRepository reviewScoreRepository;
	private final DiscountRepository discountRepository;
	private final SellerProductRepository sellerProductRepository;
	private final ProductDeliveryTypeRepository productDeliveryTypeRepository;

	// 중분류 상품 조회
	public ProductMResponseDto getProductsM(Long categoryMId, int index) {

		try {
			// pageable 객체 생성
			Pageable pageable = PageRequest.of(index, 40);
			Page<Product> productsMPage = categoryProductRepository.findByCategoryMId(categoryMId, pageable);

			List<ProductDto> products = productsMPage.stream()
				.map(this::mapProductDto)
				.collect(Collectors.toList());

			return ProductMResponseDto.builder()
				.products(products)
				.build();
		} catch (CustomException exception) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		}
	}

	// 상품 조회 response 매핑 메서드
	public ProductDto mapProductDto(Product product) {

		Long productId = product.getProductId();
		List<Discount> discount = discountRepository.findByProductProductId(productId);
		List<ReviewScore> reviewScore = reviewScoreRepository.findByProductProductId(productId);
		List<Seller> seller = sellerProductRepository.findByProductId(productId);
		List<DeliveryType> deliveryType = productDeliveryTypeRepository.findByProductId(productId);

		List<DiscountDto.Response> discounts = discount.stream()
			.map(DiscountDto::mapDiscountDto)
			.collect(Collectors.toList());

		List<ReviewScoreDto.Response> reviews = reviewScore.stream()
			.map(ReviewScoreDto::mapReviewScoreDto)
			.collect(Collectors.toList());

		List<SellerDto.Response> sellers = seller.stream()
			.map(SellerDto::mapSellerDto)
			.collect(Collectors.toList());

		List<DeliveryTypeDto.Response> deliveryTypes = deliveryType.stream()
			.map(DeliveryTypeDto::mapDeliveryTypeDto)
			.collect(Collectors.toList());

		AtomicLong id = new AtomicLong();
		return ProductDto.builder()
			.id(id.getAndIncrement())
			.productId(productId)
			.productName(product.getProductName())
			.adultSales(product.getAdultSales())
			.price(product.getPrice())
			.status(product.getStatus())
			.discounts(discounts)
			.reviews(reviews)
			.sellers(sellers)
			.deliveryTypes(deliveryTypes)
			.build();
	}

}