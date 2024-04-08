package com.isekai.ssgserver.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ReviewReqDto {
	byte score;
	String reviewContent;
	String reviewImage;
	Long productId;
}
