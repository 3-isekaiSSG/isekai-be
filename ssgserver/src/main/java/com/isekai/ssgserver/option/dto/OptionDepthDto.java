package com.isekai.ssgserver.option.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class OptionDepthDto {

	private int depth;
	private String category;
}
