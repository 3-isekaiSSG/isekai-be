package com.isekai.ssgserver.product.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductDetailDto {
	// 상품코드
	private String productCode;
	// 	상품 이름 (outer)
	private String productName;
	// 상품 디테일
	private String productDetail;
	// 상품 상태
	private int status;
	// 생성일자
	private LocalDateTime createdAt;
	// 	정가
	private int originPrice;
	// 성인 판매 여부
	private int adultSales;

}
