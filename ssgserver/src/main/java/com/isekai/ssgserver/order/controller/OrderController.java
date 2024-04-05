package com.isekai.ssgserver.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.order.dto.MemberOrderDto;
import com.isekai.ssgserver.order.dto.NonMemberOrderDto;
import com.isekai.ssgserver.order.dto.OrderResponseDto;
import com.isekai.ssgserver.order.service.OrderService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
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
	private final JwtProvider jwtProvider;

	@PostMapping("")
	@Operation(summary = "회원 주문 생성", description = "토큰 필요")
	public ResponseEntity<OrderResponseDto> createMemberOrder(
		@RequestHeader("Authorization") String token,
		@RequestBody MemberOrderDto memberOrderDto
	) {

		String uuid = jwtProvider.getUuid(token);

		OrderResponseDto orderResponseDto = orderService.createMemberOrder(uuid, memberOrderDto);
		return ResponseEntity.ok(orderResponseDto);
	}

	@PostMapping("/non-member")
	@Operation(summary = "비회원 주문 생성")
	public ResponseEntity<OrderResponseDto> createNonMemberOrder(
		@RequestBody NonMemberOrderDto nonMemberOrderDto) {

		OrderResponseDto orderResponseDto = orderService.createNonMemberOrder(nonMemberOrderDto);
		return ResponseEntity.ok(orderResponseDto);
	}
}
