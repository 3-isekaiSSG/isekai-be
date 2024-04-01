package com.isekai.ssgserver.delivery.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.delivery.repository.DeliveryTypeRepository;
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
	private final DeliveryTypeRepository deliveryTypeRepository;

	public DeliveryTypeDto getDeliveryTypeIdByProduct(String productCode) {

		return productDeliveryTypeRepository.findByProductCode(productCode)
			.map(dt -> DeliveryTypeDto.builder()
				.id(0)
				.deliveryTypeId(dt.getDeliveryType().getDeliveryTypeId())
				.name(dt.getDeliveryType().getName())
				.imageUrl(dt.getDeliveryType().getImageUrl())
				.selectedImageUrl(dt.getDeliveryType().getSelectedImageUrl())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	public List<DeliveryTypeDto> getDeliveryTypeList() {
		AtomicInteger dtId = new AtomicInteger(0);

		return deliveryTypeRepository.findAll()
			.stream()
			.map(dt -> DeliveryTypeDto.builder()
				.id(dtId.getAndIncrement())
				.deliveryTypeId(dt.getDeliveryTypeId())
				.name(dt.getName())
				.imageUrl(dt.getImageUrl())
				.selectedImageUrl(dt.getSelectedImageUrl())
				.build())
			.toList();
	}
}
