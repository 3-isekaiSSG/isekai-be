package com.isekai.ssgserver.order.entity;

import com.isekai.ssgserver.common.BaseCreateTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Table(name = "orders")
public class Order extends BaseCreateTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orders_id")
	private Long ordersId;

	@Column(name = "uuid", nullable = false)
	private String uuid;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "member_coupon_id")
	private Long memberCouponId;

	@Column(name = "origin_price", nullable = false)
	private int originPrice;

	@Column(name = "discount_price", nullable = false)
	private int discountPrice;

	@Column(name = "delivery_fee", nullable = false)
	private int deliveryFee;

	@Column(name = "buy_price", nullable = false)
	private int buyPrice;

	@Column(name = "orders_name", nullable = false)
	private String ordersName;

	@Column(name = "orders_phone", nullable = false)
	private String ordersPhone;

	@Column(name = "orders_email", nullable = false)
	private String ordersEmail;

	@Builder
	public Order(String uuid, String code, Long memberCouponId, int originPrice, int discountPrice, int deliveryFee,
		int buyPrice, String ordersName, String ordersPhone, String ordersEmail) {
		this.uuid = uuid;
		this.code = code;
		this.memberCouponId = memberCouponId;
		this.originPrice = originPrice;
		this.discountPrice = discountPrice;
		this.deliveryFee = deliveryFee;
		this.buyPrice = buyPrice;
		this.ordersName = ordersName;
		this.ordersPhone = ordersPhone;
		this.ordersEmail = ordersEmail;
	}
}
