package com.isekai.ssgserver.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.delivery.dto.DeliveryStatusCountDto;
import com.isekai.ssgserver.delivery.service.DeliveryService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
@Tag(name = "Delivery", description = "배송(=판매자별 주문) 관련 API document")
public class DeliveryController {

	private final JwtProvider jwtProvider;

	private final DeliveryService deliveryService;

	@GetMapping("/status/{status}")
	@Operation(summary = "회원 주문/배송 상태별 횟수 조회", description = "0: 주문접수, 1: 결제완료, 2: 상품준비중, 3: 배송준비중, 4: 배송중, 5: 배송완료, 6: 구매확정")
	public ResponseEntity<DeliveryStatusCountDto> getDeliveryCountByStatus(
		@RequestHeader("Authorization") String token,
		@PathVariable int status
	) {
		String uuid = jwtProvider.getUuid(token);

		DeliveryStatusCountDto deliveryStatusCountDto = deliveryService.getOrderCountByStatus(uuid, status);
		return ResponseEntity.ok(deliveryStatusCountDto);
	}
}
