package com.isekai.ssgserver.delivery.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.delivery.repository.ProductDeliveryTypeRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryTypeService {

	private final ProductDeliveryTypeRepository productDeliveryTypeRepository;

	public DeliveryTypeDto getDeliveryTypeIdByProduct(String productCode) {

		return productDeliveryTypeRepository.findByProductCode(productCode)
			.map(dt -> DeliveryTypeDto.builder()
				.deliveryTypeId(dt.getDeliveryType().getDeliveryTypeId())
				.name(dt.getDeliveryType().getName())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}
}
