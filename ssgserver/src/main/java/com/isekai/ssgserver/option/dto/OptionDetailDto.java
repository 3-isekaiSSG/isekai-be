package com.isekai.ssgserver.option.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OptionDetailDto {

	private int id;
	private Long optionsId;
	private int orderLimit;
	private int stock;
	private String value;

}
