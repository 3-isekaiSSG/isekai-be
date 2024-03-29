package com.isekai.ssgserver.product.service;

import com.isekai.ssgserver.product.dto.DiscountDto;
import com.isekai.ssgserver.product.dto.ReviewScoreDto;
import com.isekai.ssgserver.product.repository.ReviewScoreRepository;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryProductCustomRepository;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.image.repository.ImageRepository;
import com.isekai.ssgserver.product.dto.ProductDetailDto;
import com.isekai.ssgserver.product.dto.ProductSummaryDto;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.repository.DiscountRepository;
import com.isekai.ssgserver.product.repository.ProductRepository;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

	// private final CategoryProductRepository categoryProductRepository;
	private final CategoryProductCustomRepository categoryProductCustomRepository;
	private final DiscountRepository discountRepository;
	private final SellerProductRepository sellerProductRepository;
	private final ProductDeliveryTypeRepository productDeliveryTypeRepository;
	private final ProductRepository productRepository;
	private final ImageRepository imageReposiroty;
	private final ReviewScoreRepository reviewScoreRepository;

	// 중분류 상품 조회
	// public ProductMResponseDto getProductsM(String mediumName, int index) {
	//
	// 	try {
	// 		// pageable 객체 생성
	// 		Pageable pageable = PageRequest.of(index, 40);
	// 		Page<Product> productsMPage = categoryProductCustomRepository.findByCategoryMName(mediumName, pageable);
	//
	// 		List<ProductDto> products = productsMPage.stream()
	// 			.map(this::mapProductDto)
	// 			.toList();
	//
	// 		return ProductMResponseDto.builder()
	// 			.products(products)
	// 			.build();
	// 	} catch (CustomException exception) {
	// 		throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
	// 	}
	// }

	// // 상품 조회 response 매핑 메서드
	// public ProductDto mapProductDto(Product product) {
	//
	// 	Long productId = product.getProductId();
	// 	List<Discount> discount = discountRepository.findByProductProductId(productId);
	// 	List<ReviewScore> reviewScore = reviewScoreRepository.findByProductProductId(productId);
	// 	List<Seller> seller = sellerProductRepository.findByProductId(productId);
	// 	List<DeliveryType> deliveryType = productDeliveryTypeRepository.findByProductId(productId);
	//
	// 	List<DiscountDto.Response> discounts = discount.stream()
	// 		.map(DiscountDto::mapDiscountDto)
	// 		.toList();
	//
	// 	List<ReviewScoreDto.Response> reviews = reviewScore.stream()
	// 		.map(ReviewScoreDto::mapReviewScoreDto)
	// 		.toList();
	//
	// 	List<SellerDto.Response> sellers = seller.stream()
	// 		.map(SellerDto::mapSellerDto)
	// 		.toList();
	//
	// 	List<DeliveryTypeDto.Response> deliveryTypes = deliveryType.stream()
	// 		.map(DeliveryTypeDto::mapDeliveryTypeDto)
	// 		.toList();
	//
	// 	AtomicLong id = new AtomicLong();
	// 	return ProductDto.builder()
	// 		.id(id.getAndIncrement())
	// 		.productId(productId)
	// 		.productName(product.getProductName())
	// 		.adultSales(product.getAdultSales())
	// 		.price(product.getPrice())
	// 		.status(product.getStatus())
	// 		.discounts(discounts)
	// 		.reviews(reviews)
	// 		.sellers(sellers)
	// 		.deliveryTypes(deliveryTypes)
	// 		.build();
	// }
	//

	/**
	 * 상품 리스트의 요약된 카드 형식 데이터 조회
	 * @param productCode 상품 코드
	 * @return
	 */
	public ProductSummaryDto getProductInfo(String productCode) {
		Product product = productRepository.findByCode(productCode)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return ProductSummaryDto.builder()
				.productCode(product.getCode())
				.productName(product.getProductName())
				.status(product.getStatus())
				.createdAt(product.getCreatedAt())
				.originPrice(product.getPrice())
				.adultSales(product.getAdultSales())
				.build();
	}

	/**
	 * 상품 상세 페이지의 상단 ~ 상품 디테일까지 조회
	 * @param productCode 상품 코드
	 * @return
	 */
	public ProductDetailDto getProductDetail(String productCode) {
		Product product = productRepository.findByCode(productCode)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return ProductDetailDto.builder()
				.productCode(product.getCode())
				.productName(product.getProductName())
				.productDetail(product.getProductDetail())
				.status(product.getStatus())
				.createdAt(product.getCreatedAt())
				.originPrice(product.getPrice())
				.adultSales(product.getAdultSales())
				.build();
	}

	/**
	 * 상품의 할인 정보 조회(할인율, 할인가)
	 * @param productCode
	 * @return
	 */
	public DiscountDto getDiscountByProduct(String productCode) {

		return discountRepository.findByProductCode(productCode)
				.map(d -> DiscountDto.builder()
						.discountRate(d.getDiscountRate())
						.discountPrice(d.getDiscountPrice())
						.build())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	/**
	 * 상품의 리뷰 집계 조회 (총 리뷰 개수, 리뷰 평점)
	 * @param productCode
	 * @return
	 */
	public ReviewScoreDto getReviewScoreByProduct(String productCode) {

		return reviewScoreRepository.findByProductCode(productCode)
				.map(rs -> ReviewScoreDto.builder()
						.reviewCount(rs.getReviewCount())
						.totalScore(rs.getTotalScore())
						.avgScore(rs.getAvgScore())
						.build())
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}


}


