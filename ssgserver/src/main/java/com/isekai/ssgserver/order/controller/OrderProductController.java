package com.isekai.ssgserver.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.order.dto.OrderProductDetailDto;
import com.isekai.ssgserver.order.dto.OrderProductListDto;
import com.isekai.ssgserver.order.service.OrderProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-products")
@Tag(name = "OrderProduct", description = "주문 상품 관련 API document")
public class OrderProductController {

	private final OrderProductService orderProductService;

	@GetMapping("/deliveries/{deliveryId}")
	@Operation(summary = "배송(delivery)별 주문상품 리스트 조회")
	public ResponseEntity<List<OrderProductListDto>> getOrderProductListByDelivery(@PathVariable Long deliveryId) {

		List<OrderProductListDto> orderProductListDtoList = orderProductService.getOrderProductListByDelivery(
			deliveryId);
		return ResponseEntity.ok(orderProductListDtoList);
	}

	@GetMapping("/{orderProductId}")
	@Operation(summary = "주문상품 상세 조회")
	public ResponseEntity<OrderProductDetailDto> getOrderProductDetail(@PathVariable Long orderProductId) {

		OrderProductDetailDto orderProductDetailDto = orderProductService.getOrderProductDetail(orderProductId);
		return ResponseEntity.ok(orderProductDetailDto);
	}

}
