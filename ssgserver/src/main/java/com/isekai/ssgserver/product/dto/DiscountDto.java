package com.isekai.ssgserver.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DiscountDto {

    private boolean discounted;
    private int discountRate;
    private int discountPrice;

}
