package com.isekai.ssgserver.order.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.delivery.entity.Delivery;
import com.isekai.ssgserver.delivery.repository.DeliveryRepository;
import com.isekai.ssgserver.deliveryAddress.entity.DeliveryAddress;
import com.isekai.ssgserver.deliveryAddress.repository.DeliveryAddressRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.repository.MemberRepository;
import com.isekai.ssgserver.order.dto.MemberOrderDto;
import com.isekai.ssgserver.order.dto.NonMemberOrderDto;
import com.isekai.ssgserver.order.dto.OrderListDto;
import com.isekai.ssgserver.order.dto.OrderProductDto;
import com.isekai.ssgserver.order.dto.OrderResponseDto;
import com.isekai.ssgserver.order.dto.OrderSellerProductDto;
import com.isekai.ssgserver.order.dto.OrderSummaryDto;
import com.isekai.ssgserver.order.entity.Order;
import com.isekai.ssgserver.order.entity.OrderProduct;
import com.isekai.ssgserver.order.repository.OrderCustomRepository;
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
	private final MemberRepository memberRepository;
	private final OrderCustomRepository orderCustomRepository;

	/**
	 * 비회원 주문 생성
	 */
	public OrderResponseDto createNonMemberOrder(NonMemberOrderDto nonMemberOrderDto) {

		// 배송지 저장
		DeliveryAddress deliveryAddress = DeliveryAddress.builder()
			.memberId(-1L)
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
			.uuid("NONMEMBER")
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
				.uuid("NONMEMBER")
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
						.originPrice(opd.getOriginPrice())
						.is_confirm(false)
						.productCode(opd.getProductCode())
						.delivery(savedSellerDelivery)
						.build();
					orderProductRepository.save(orderProduct); // 각 OrderProduct 객체를 저장
				});
		}

		// 주문_상품 생성 (개별 상품-옵션)

		return OrderResponseDto.builder()
			.orderCode(savedOrder.getCode())
			.build();

	}

	/**
	 * 회원 주문 생성
	 */
	public OrderResponseDto createMemberOrder(String uuid, MemberOrderDto memberOrderDto) {

		// 멤버 있는지 확인
		if (!memberRepository.existsByUuidAndIsWithdraw(uuid, (byte)0)) {
			throw new CustomException(ErrorCode.NOT_FOUND_USER);
		}

		// 배송지 가져오기
		DeliveryAddress memberAddress = deliveryAddressRepository.findById(memberOrderDto.getDeliveryAddressId())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
		log.info("배송지 = " + memberAddress.toString());

		// 주문코드 생성
		String orderCode = generateOrderCode();
		log.info("주문코드 = " + orderCode);

		// 주문 생성
		Order order = Order.builder()
			.uuid(uuid)
			.code(orderCode)
			.memberCouponId(null)  // todo : order 쿠폰 기능 적용
			.originPrice(memberOrderDto.getOriginPrice())
			.discountPrice(memberOrderDto.getDiscountPrice())
			.deliveryFee(memberOrderDto.getDeliveryFee())
			.buyPrice(memberOrderDto.getBuyPrice())
			.ordersName(memberOrderDto.getOrderName())
			.ordersPhone(memberOrderDto.getOrderPhone())
			.ordersEmail(memberOrderDto.getOrderEmail())
			.build();
		log.info("주문 : " + order.toString());

		// 주문 DB 저장
		Order savedOrder = orderRepository.save(order);

		for (OrderSellerProductDto orderSellerProductDto : memberOrderDto.getOrderSellerProductDtoList()) {

			int sellerOriginPrice = 0;
			int sellerBuyPrice = 0;
			for (OrderProductDto orderProductDto : orderSellerProductDto.getOrderProductList()) {
				sellerOriginPrice += (orderProductDto.getOriginPrice() * orderProductDto.getCount());
				sellerBuyPrice += (orderProductDto.getBuyPrice() * orderProductDto.getCount());
			}

			// 배송(판매자별) DB 저장
			Delivery sellerDelivery = Delivery.builder()
				.status(0)
				.uuid(uuid)
				.deliveryType(orderSellerProductDto.getDelivertType())
				.seller(orderSellerProductDto.getSellerName())
				.buyPrice(sellerBuyPrice)
				.originPrice(sellerOriginPrice)
				.deliveryFee(orderSellerProductDto.getDeliveryFee())
				.deliveryCompany(null)
				.deliveryCode(null)
				.deliveryMessage(memberOrderDto.getDeliveryMessage())
				.order(savedOrder)
				.deliveryAddress(memberAddress)
				.build();
			log.info("판매자 배송 = " + sellerDelivery.toString());

			Delivery savedSellerDelivery = deliveryRepository.save(sellerDelivery);

			// 주문_상품 DB 저장
			orderSellerProductDto.getOrderProductList()
				.forEach(opd -> {
					OrderProduct orderProduct = OrderProduct.builder()
						.count(opd.getCount())
						.buyPrice(opd.getBuyPrice())
						.originPrice(opd.getOriginPrice())
						.is_confirm(false)
						.productCode(opd.getProductCode())
						.delivery(savedSellerDelivery)
						.build();
					orderProductRepository.save(orderProduct); // 각 OrderProduct 객체를 저장
				});
		}

		// 주문_상품 생성 (개별 상품-옵션)

		return OrderResponseDto.builder()
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

	public OrderSummaryDto getOrderSummary(String orderCode) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

		return orderRepository.findByCode(orderCode)
			.map(o -> OrderSummaryDto.builder()
				.date(o.getCreatedAt().format(formatter))
				.orderId(o.getOrdersId())
				.buyPrice(o.getBuyPrice())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	public List<OrderListDto> getOrderList(String uuid, Integer month, LocalDate endDate, byte dType) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		AtomicInteger id = new AtomicInteger(0);

		List<Order> orders = orderCustomRepository.findByUuidAndFilter(uuid, month, endDate, dType);

		return orders.stream()
			.map(ol -> OrderListDto.builder()
				.id(id.getAndIncrement())
				.ordersId(ol.getOrdersId())
				.code(ol.getCode())
				.buyPrice(ol.getBuyPrice())
				.date(ol.getCreatedAt().format(formatter))
				.build())
			.toList();
	}
}
