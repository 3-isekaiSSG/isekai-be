package com.isekai.ssgserver.product.dto;

import com.isekai.ssgserver.product.entity.Discount;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DiscountDto {

	@Getter
	@Builder
	public static class Response {
		private Long dicountId;
		private Long discountRate;
		private Long discountPrice;
	}

	public static Response mapDiscountDto(Discount discount) {
		return Response.builder()
			.dicountId(discount.getDiscountId())
			.discountRate((long)discount.getDiscountRate())
			.discountPrice((long)discount.getDiscountPrice())
			.build();
	}

}
