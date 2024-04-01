package com.isekai.ssgserver.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.delivery.service.DeliveryTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-types")
@Tag(name = "Delivery Type", description = "배송 유형 조회 API document")
public class DeliveryTypeController {

	private final DeliveryTypeService deliveryTypeService;

	@GetMapping("/")
	@Operation(summary = "배송 유형 조회 - 단일 상품", description = "1: 쓱 배송, 2: 일반 택배 배송")
	public ResponseEntity<List<DeliveryTypeDto>> getDeliveryTypeList() {
		List<DeliveryTypeDto> deliveryTypeDtoList = deliveryTypeService.getDeliveryTypeList();
		return ResponseEntity.ok(deliveryTypeDtoList);
	}

	@GetMapping("/products/{productCode}")
	@Operation(summary = "배송 유형 조회 - 단일 상품", description = "1: 쓱 배송, 2: 일반 택배 배송")
	public ResponseEntity<DeliveryTypeDto> getProductDeliveryType(@PathVariable String productCode) {
		DeliveryTypeDto deliveryTypeDto = deliveryTypeService.getDeliveryTypeIdByProduct(productCode);
		return ResponseEntity.ok(deliveryTypeDto);
	}
}
