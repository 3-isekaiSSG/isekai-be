package com.isekai.ssgserver.order.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.order.dto.OrderProductDetailDto;
import com.isekai.ssgserver.order.dto.OrderProductListDto;
import com.isekai.ssgserver.order.repository.OrderProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProductService {

	private final OrderProductRepository orderProductRepository;

	public List<OrderProductListDto> getOrderProductListByDelivery(Long deliveryId) {
		AtomicInteger id = new AtomicInteger();

		return orderProductRepository.findAllByDeliveryDeliveryId(deliveryId)
			.stream()
			.map(op -> OrderProductListDto.builder()
				.id(id.getAndIncrement())
				.orderProductId(op.getOrderProductId())
				.build())
			.toList();
	}

	public OrderProductDetailDto getOrderProductDetail(Long orderProductId) {

		return orderProductRepository.findById(orderProductId)
			.map(op -> OrderProductDetailDto.builder()
				.productCode(op.getProductCode())
				.count(op.getCount())
				.buyPrice(op.getBuyPrice())
				.originPrice(op.getOriginPrice())
				.isConfirm(op.is_confirm())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}
}
