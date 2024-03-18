package com.isekai.ssgserver.order.entity;

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
@Builder
@ToString
@Getter
@Table(name = "order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private long orderId;

	@Column(name = "member_id", nullable = false)
	private long memberId;

	@Column(nullable = false)
	private String code;

	@Column(name = "member_coupon_id")
	private long memeberCouponId;

	@Column(name = "regular_price",nullable = false)
	private int regularPrice;

	@Column(name = "discount_price", nullable = false)
	private int discountPrice;

	@Column(name = "delivery_price", nullable = false)
	private int deliveryPrice;

	@Column(name = "pay_price", nullable = false)
	private int payPrice;

	@Column (name = "order_name", nullable = false)
	private String orderName;

	@Column (name = "order_phone", nullable = false)
	private String orderPhone;

	@Column (name = "order_email", nullable = false)
	private String orderEmail;

}
