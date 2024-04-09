package com.isekai.ssgserver.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewReqDto {
	byte score;
	String reviewContent;
	String reviewImage;
	Long productId;
}
