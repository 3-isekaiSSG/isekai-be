package com.isekai.ssgserver.product.dto;

import java.util.List;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.review.dto.ReviewScoreDto;
import com.isekai.ssgserver.seller.dto.SellerDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

	// private Long id;
	private Long productId;
	private String productName;
	private int price;
	private int adultSales;
	private int status;

	private List<DiscountDto> discounts;
	private List<ReviewScoreDto> reviews;
	private List<SellerDto> sellers;
	private List<DeliveryTypeDto> deliveryTypes;
}
