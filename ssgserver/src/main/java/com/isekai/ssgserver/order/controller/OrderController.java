package com.isekai.ssgserver.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.order.dto.NonMemberOrderDto;
import com.isekai.ssgserver.order.dto.NonMemberOrderResponseDto;
import com.isekai.ssgserver.order.service.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Order", description = "주문 관련 API document")
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/non-member")
	public ResponseEntity<NonMemberOrderResponseDto> createNonMemberOrder(
		@RequestBody NonMemberOrderDto nonMemberOrderDto) {

		NonMemberOrderResponseDto nonMemberOrderResponseDto = orderService.createNonMemberOrder(nonMemberOrderDto);
		return ResponseEntity.ok(nonMemberOrderResponseDto);
	}
}
