package com.isekai.ssgserver.product.dto;

import java.util.List;

import com.isekai.ssgserver.review.dto.ReviewScoreDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductMResponseDto {
	private List<ProductDto> products;
}
