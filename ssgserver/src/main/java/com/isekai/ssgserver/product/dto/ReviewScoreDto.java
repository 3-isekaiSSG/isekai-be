package com.isekai.ssgserver.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ReviewScoreDto {

    private Long reviewCount;
    private Long totalScore;
    private double avgScore;

}
