package com.isekai.ssgserver.seller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SellerDto {

    private Long sellerId;
    private String name;
}
