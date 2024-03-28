package com.isekai.ssgserver.seller.dto;

import com.isekai.ssgserver.seller.entity.Seller;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SellerDto {

	@Getter
	@Builder
	public static class Response {
		private Long sellerId;
		private String name;
	}

	public static Response mapSellerDto(Seller seller) {
		return Response.builder()
			.sellerId(seller.getSellerId())
			.name(seller.getName())
			.build();
	}
}
