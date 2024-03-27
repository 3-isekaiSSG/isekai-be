package com.isekai.ssgserver.product.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.repository.CategoryProductCustomRepository;
import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.delivery.entity.DeliveryType;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.image.dto.ImageDto;
import com.isekai.ssgserver.image.entity.Image;
import com.isekai.ssgserver.image.repository.ImageRepository;
import com.isekai.ssgserver.product.dto.DiscountDto;
import com.isekai.ssgserver.product.dto.ProductDetailDto;
import com.isekai.ssgserver.product.dto.ProductDto;
import com.isekai.ssgserver.product.dto.ProductMResponseDto;
import com.isekai.ssgserver.product.dto.ProductSummaryDto;
import com.isekai.ssgserver.product.entity.Discount;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.repository.DiscountRepository;
import com.isekai.ssgserver.product.repository.ProductRepository;
import com.isekai.ssgserver.review.dto.ReviewScoreDto;
import com.isekai.ssgserver.review.entity.ReviewScore;
import com.isekai.ssgserver.review.repository.ReviewScoreRepository;
import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.entity.Seller;
import com.isekai.ssgserver.seller.entity.SellerProduct;
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

	// 중분류 상품 조회
	public ProductMResponseDto getProductsM(String mediumName, int index) {

		try {
			// pageable 객체 생성
			Pageable pageable = PageRequest.of(index, 40);
			Page<Product> productsMPage = categoryProductCustomRepository.findByCategoryMName(mediumName, pageable);

			List<ProductDto> products = productsMPage.stream()
				.map(this::mapProductDto)
				.toList();

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
			.toList();

		List<ReviewScoreDto.Response> reviews = reviewScore.stream()
			.map(ReviewScoreDto::mapReviewScoreDto)
			.toList();

		List<SellerDto.Response> sellers = seller.stream()
			.map(SellerDto::mapSellerDto)
			.toList();

		List<DeliveryTypeDto.Response> deliveryTypes = deliveryType.stream()
			.map(DeliveryTypeDto::mapDeliveryTypeDto)
			.toList();

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

	/**
	 * 상품 리스트의 요약된 카드 형식 데이터 조회
	 * @param productCode 상품 코드
	 * @return
	 */
	public ProductSummaryDto getProductSummary(String productCode) {
		Product product = productRepository.findByCode(productCode)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
		Long deliveryTypeId = getDeliveryTypeIdByProduct(product);
		Seller seller = getSellerByProduct(product);
		Discount discount = getDiscountByProduct(product);
		ReviewScore reviewScore = getReviewScoreByProduct(product);
		String imageUrl = imageReposiroty.findByProductAndIsThumbnail(product, 1)
			.map(Image::getImageUrl)
			.orElse("defaultUrl");

		return ProductSummaryDto.builder()
			.productCode(product.getCode())
			.deliveryTypeId(deliveryTypeId)
			.image(imageUrl)
			.productName(product.getProductName())
			.sellerId(seller.getSellerId())
			.sellerName(seller.getName())
			.originPrice(product.getPrice())
			.discountPrice(discount.getDiscountPrice())
			.discountRate(discount.getDiscountRate())
			.adultSales(product.getAdultSales())
			.avgScore(reviewScore.getAvgScore())
			.reviewCount(reviewScore.getReviewCount())
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
		Long deliveryTypeId = getDeliveryTypeIdByProduct(product);
		Seller seller = getSellerByProduct(product);
		Discount discount = getDiscountByProduct(product);
		ReviewScore reviewScore = getReviewScoreByProduct(product);
		List<ImageDto> imageDtoList = imageReposiroty.findAllByProduct(product)
			.stream()
			.map(image -> ImageDto.builder()
				.imageId(image.getImageId())
				.isThumbnail(image.getIsThumbnail())
				.seq(image.getSeq())
				.imageUrl(image.getImageUrl())
				.build())
			.toList();

		return ProductDetailDto.builder()
			.productCode(product.getCode())
			.deliveryTypeId(deliveryTypeId)
			.images(imageDtoList)
			.productName(product.getProductName())
			.productDetail(product.getProductDetail())
			.sellerId(seller.getSellerId())
			.sellerName(seller.getName())
			.originPrice(product.getPrice())
			.discountPrice(discount.getDiscountPrice())
			.discountRate(discount.getDiscountRate())
			.adultSales(product.getAdultSales())
			.avgScore(reviewScore.getAvgScore())
			.reviewCount(reviewScore.getReviewCount())
			.build();
	}

	public Seller getSellerByProduct(Product product) {
		return sellerProductRepository.findByProduct(product)
			.map(SellerProduct::getSeller)
			.orElse(null);
	}

	public Discount getDiscountByProduct(Product product) {
		return discountRepository.findByProduct(product)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	public ReviewScore getReviewScoreByProduct(Product product) {
		return reviewScoreRepository.findByProduct(product)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	public Long getDeliveryTypeIdByProduct(Product product) {
		return productDeliveryTypeRepository.findFirstByProduct(product)
			.map(productDeliveryType -> productDeliveryType.getDeliveryType().getDeliveryTypeId())
			.orElse(null);
	}
}


