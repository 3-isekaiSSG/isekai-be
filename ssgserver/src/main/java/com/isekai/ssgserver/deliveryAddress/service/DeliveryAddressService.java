package com.isekai.ssgserver.deliveryAddress.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.deliveryAddress.dto.DeliveryAddressNicknameDto;
import com.isekai.ssgserver.deliveryAddress.repository.DeliveryAddressRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryAddressService {

	private final DeliveryAddressRepository deliveryAddressRepository;

	public DeliveryAddressNicknameDto getDeliveryAddressNickname(Long deliveryAddressId) {

		return deliveryAddressRepository.findById(deliveryAddressId)
			.map(da -> DeliveryAddressNicknameDto.builder()
				.nickname(da.getNickname())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

}
