package com.isekai.ssgserver.product.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CategoryProductResponseDto {

	private Integer id;
	private String largeName;
	private String mediumName;
	private String smallName;
	private Integer total;
	private Integer curPage;
	private Integer lastPage;

	private List<ProductInfoDto> products;
}
