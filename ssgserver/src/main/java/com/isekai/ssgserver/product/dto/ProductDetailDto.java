package com.isekai.ssgserver.product.dto;

import java.util.List;

import com.isekai.ssgserver.image.dto.ImageDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductDetailDto {
	// 상품코드
	private String productCode;
	// 	배송유형
	private Long deliveryTypeId;
	//  사진
	private List<ImageDto> images;
	// 	상품 이름 (outer)
	private String productName;
	// 상품 디테일
	private String productDetail;
	// 	판매자 pk
	private Long sellerId;
	// 판매자 이름
	private String sellerName;
	// 	정가
	private int originPrice;
	// 	할인가
	private int discountPrice;
	// 할인율
	private int discountRate;
	// 성인 판매 여부
	private int adultSales;
	// 	평점 평균
	private double avgScore;
	// 	리뷰 개수
	private Long reviewCount;
}
