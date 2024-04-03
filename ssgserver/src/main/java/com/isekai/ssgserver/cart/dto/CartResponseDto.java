package com.isekai.ssgserver.cart.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartResponseDto {

	private Integer id;
	private List<CartInfoDto> normal;
	private List<CartInfoDto> ssg;

}
