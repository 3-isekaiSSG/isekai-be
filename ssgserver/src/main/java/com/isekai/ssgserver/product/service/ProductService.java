package com.isekai.ssgserver.product.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryProductCustomRepository;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.image.repository.ImageRepository;
import com.isekai.ssgserver.product.dto.CategoryProductResponseDto;
import com.isekai.ssgserver.product.dto.DiscountDto;
import com.isekai.ssgserver.product.dto.ProductDetailDto;
import com.isekai.ssgserver.product.dto.ProductInfoDto;
import com.isekai.ssgserver.product.dto.ProductSortOptionResponseDto;
import com.isekai.ssgserver.product.dto.ProductSummaryDto;
import com.isekai.ssgserver.product.dto.ReviewScoreDto;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.enums.ProductSortOption;
import com.isekai.ssgserver.product.repository.DiscountRepository;
import com.isekai.ssgserver.product.repository.ProductRepository;
import com.isekai.ssgserver.product.repository.ReviewScoreRepository;
import com.isekai.ssgserver.seller.repository.SellerProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

	private final CategoryProductCustomRepository categoryProductCustomRepository;

	private final DiscountRepository discountRepository;
	private final SellerProductRepository sellerProductRepository;
	private final ProductDeliveryTypeRepository productDeliveryTypeRepository;
	private final ProductRepository productRepository;
	private final ImageRepository imageReposiroty;
	private final ReviewScoreRepository reviewScoreRepository;

	// 카테고리 상품 조회
	public CategoryProductResponseDto getCategoryProduct(String largeName, String mediumName, String smallName,
		Integer index, String criteria, String brandName, String dType, Integer minPrc, Integer maxPrc) {

		// pageable 객체 생성
		Pageable pageable = PageRequest.of(index, 40);
		Page<Product> productPage = categoryProductCustomRepository.findCategoryProduct(
			largeName, mediumName, smallName, criteria, brandName, dType, minPrc, maxPrc, pageable);
		AtomicInteger infoId = new AtomicInteger(0);
		AtomicInteger responseId = new AtomicInteger(0);

		// 조회된 상품 정보를 ProductInfoDto 리스트로 변환
		List<ProductInfoDto> productInfoDtos = productPage.getContent().stream()
			.map(product -> ProductInfoDto.builder()
				.id(infoId.getAndIncrement())
				.code(product.getCode())
				.build())
			.toList();

		// CategoryProductResponseDto 객체 생성 및 반환
		return CategoryProductResponseDto.builder()
			.id(responseId.getAndIncrement())
			.largeName(largeName)
			.mediumName(mediumName)
			.smallName(smallName)
			.total((int)productPage.getTotalElements())
			.curPage(productPage.getNumber())
			.lastPage(productPage.getTotalPages())
			.products(productInfoDtos)
			.build();
	}

	/**
	 * 상품 리스트의 요약된 카드 형식 데이터 조회
	 *
	 * @param productCode 상품 코드
	 * @return
	 */
	public ProductSummaryDto getProductInfo(String productCode) {
		Product product = productRepository.findByCode(productCode)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return ProductSummaryDto.builder()
			.code(product.getCode())
			.name(product.getProductName())
			.status(product.getStatus())
			.createdAt(product.getCreatedAt())
			.originPrice(product.getPrice())
			.adultSales(product.getAdultSales())
			.build();
	}

	/**
	 * 상품 상세 페이지의 상단 ~ 상품 디테일까지 조회
	 *
	 * @param productCode 상품 코드
	 * @return
	 */
	public ProductDetailDto getProductDetail(String productCode) {
		Product product = productRepository.findByCode(productCode)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return ProductDetailDto.builder()
			.code(product.getCode())
			.name(product.getProductName())
			.detail(product.getProductDetail())
			.status(product.getStatus())
			.createdAt(product.getCreatedAt())
			.originPrice(product.getPrice())
			.adultSales(product.getAdultSales())
			.build();
	}

	/**
	 * 상품의 할인 정보 조회(할인율, 할인가)
	 *
	 * @param productCode
	 * @return
	 */
	public DiscountDto getDiscountByProduct(String productCode) {

		return discountRepository.findByProductCode(productCode)
			.map(d -> DiscountDto.builder()
				.discounted(d.getDiscountRate() == 0 ? false : true)
				.discountRate(d.getDiscountRate())
				.discountPrice(d.getDiscountPrice())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	/**
	 * 상품의 리뷰 집계 조회 (총 리뷰 개수, 리뷰 평점)
	 *
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

	public List<ProductSortOptionResponseDto> getProductSortOption() {
		return Arrays.stream(ProductSortOption.values())
			.map(option -> ProductSortOptionResponseDto.builder()
				.id(option.ordinal()) // Enum의 순서를 ID로 사용
				.option(option.getDescription())
				.value(option.getCode())
				.isInfo(option.getIsInfo())
				.build())
			.toList();
	}
}