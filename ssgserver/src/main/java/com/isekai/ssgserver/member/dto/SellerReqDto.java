package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SellerReqDto {
	private Long sellerId;
	private String uuid;
}
