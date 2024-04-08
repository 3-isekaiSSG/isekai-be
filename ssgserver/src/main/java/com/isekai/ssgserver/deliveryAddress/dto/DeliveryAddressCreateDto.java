package com.isekai.ssgserver.deliveryAddress.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DeliveryAddressCreateDto {

    private String nickname;
    private String name;
    private String cellphone;
    private String telephone; // 생략가능
    private String zipcode;
    private String address;
}
