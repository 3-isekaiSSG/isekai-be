package com.isekai.ssgserver.deliveryAddress.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.deliveryAddress.dto.DeliveryAddressNicknameDto;
import com.isekai.ssgserver.deliveryAddress.service.DeliveryAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-addresses")
@Tag(name = "DeliveryAddress", description = "배송지 관련 API document")
public class DeliveryAddressController {

	private final DeliveryAddressService deliveryAddressService;

	@GetMapping("/{deliveryAddressId}/nickname")
	@Operation(summary = "배송지 별칭 조회", description = "사용자 지정 별칭 조회")
	public ResponseEntity<DeliveryAddressNicknameDto> getDeliveryAddressNickname(@PathVariable Long deliveryAddressId) {

		DeliveryAddressNicknameDto nicknameDto = deliveryAddressService.getDeliveryAddressNickname(deliveryAddressId);
		return ResponseEntity.ok(nicknameDto);
	}
}
