package com.isekai.ssgserver.order.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.delivery.entity.Delivery;
import com.isekai.ssgserver.delivery.repository.DeliveryRepository;
import com.isekai.ssgserver.deliveryAddress.entity.DeliveryAddress;
import com.isekai.ssgserver.deliveryAddress.repository.DeliveryAddressRepository;
import com.isekai.ssgserver.order.dto.NonMemberOrderDto;
import com.isekai.ssgserver.order.dto.NonMemberOrderResponseDto;
import com.isekai.ssgserver.order.dto.OrderProductDto;
import com.isekai.ssgserver.order.dto.OrderSellerProductDto;
import com.isekai.ssgserver.order.entity.Order;
import com.isekai.ssgserver.order.entity.OrderProduct;
import com.isekai.ssgserver.order.repository.OrderProductRepository;
import com.isekai.ssgserver.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final DeliveryRepository deliveryRepository;
	private final DeliveryAddressRepository deliveryAddressRepository;
	private final OrderProductRepository orderProductRepository;

	public NonMemberOrderResponseDto createNonMemberOrder(NonMemberOrderDto nonMemberOrderDto) {

		// 배송지 저장
		DeliveryAddress deliveryAddress = DeliveryAddress.builder()
			.memberId(null)
			.nickname("자택")
			.name(nonMemberOrderDto.getOrderName())
			.cellphone(nonMemberOrderDto.getOrderPhone())
			.telephone(nonMemberOrderDto.getOrderPhone())
			.zipcode(nonMemberOrderDto.getZipCode())
			.address(nonMemberOrderDto.getAddress())
			.isDefault(true)
			.isDeleted(false)
			.orderHistory(true)
			.build();

		DeliveryAddress nonMemberAddress = deliveryAddressRepository.save(deliveryAddress);

		// 주문코드 생성
		String orderCode = generateOrderCode();
		log.info("주문코드 = " + orderCode);

		// 주문 생성
		Order order = Order.builder()
			.memberId(-1L)
			.code(orderCode)
			.memberCouponId(null)
			.originPrice(nonMemberOrderDto.getOriginPrice())
			.discountPrice(0)
			.deliveryFee(nonMemberOrderDto.getDeliveryFee())
			.buyPrice(nonMemberOrderDto.getOriginPrice())
			.ordersName(nonMemberOrderDto.getOrderName())
			.ordersPhone(nonMemberOrderDto.getOrderPhone())
			.ordersEmail(nonMemberOrderDto.getOrderEmail())
			.build();
		log.info("주문 : " + order.toString());

		// 주문 DB 저장
		Order savedOrder = orderRepository.save(order);

		for (OrderSellerProductDto orderSellerProductDto : nonMemberOrderDto.getOrderSellerProductDtoList()) {

			int sellerOriginPrice = 0;
			int sellerBuyPrice = 0;
			for (OrderProductDto orderProductDto : orderSellerProductDto.getOrderProductList()) {
				sellerOriginPrice += (orderProductDto.getOriginPrice() * orderProductDto.getCount());
				sellerBuyPrice += (orderProductDto.getBuyPrice() * orderProductDto.getCount());
			}

			// 배송(판매자별) DB 저장
			Delivery sellerDelivery = Delivery.builder()
				.status(0)
				.deliveryType(orderSellerProductDto.getDelivertType())
				.seller(orderSellerProductDto.getSellerName())
				.buyPrice(sellerBuyPrice)
				.originPrice(sellerOriginPrice)
				.deliveryFee(orderSellerProductDto.getDeliveryFee())
				.deliveryCompany(null)
				.deliveryCode(null)
				.deliveryMessage(nonMemberOrderDto.getDeliveryMessage())
				.order(savedOrder)
				.deliveryAddress(nonMemberAddress)
				.build();
			log.info("판매자 배송 = " + sellerDelivery.toString());

			Delivery savedSellerDelivery = deliveryRepository.save(sellerDelivery);

			// 주문_상품 DB 저장
			orderSellerProductDto.getOrderProductList()
				.forEach(opd -> {
					OrderProduct orderProduct = OrderProduct.builder()
						.count(opd.getCount())
						.buyPrice(opd.getBuyPrice())
						.is_confirm(false)
						.productCode(opd.getProductCode())
						.delivery(savedSellerDelivery)
						.build();
					orderProductRepository.save(orderProduct); // 각 OrderProduct 객체를 저장
				});
		}

		// 주문_상품 생성 (개별 상품-옵션)

		return NonMemberOrderResponseDto.builder()
			.orderCode(savedOrder.getCode())
			.build();

	}

	/**
	 * 주문 번호 생성 메서드
	 *  - 생성 규칙 : YYYYMMDD-{orderId} (추후 변경 예정)
	 * @return
	 */
	private String generateOrderCode() {
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		Long lastId = orderRepository.findLastOrdersId().orElse(0L);
		long sequence = lastId + 1;
		return date + "-" + sequence;
	}

}
